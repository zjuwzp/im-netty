package com.zhss.im.dispathcer;

import com.alibaba.fastjson.JSONObject;
import com.zhss.im.common.Constants;
import com.zhss.im.common.Request;
import com.zhss.im.common.Response;
import com.zhss.im.protocol.MessagePushRequestProto;
import com.zhss.im.protocol.MessageSendResponseProto;
import io.netty.channel.socket.SocketChannel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka管理组件
 */
public class KafkaManager {

    static class Singleton {

        static KafkaManager instance = new KafkaManager();

    }

    public static KafkaManager getInstance() {
        return Singleton.instance;
    }

    private KafkaProducer producer;

    public KafkaManager() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 获取KafkaProducer
     * @return
     */
    public KafkaProducer getProducer() {
        return producer;
    }

    /**
     * 初始化
     */
    public void init() {
        new Thread() {

            @Override
            public void run() {
                Properties properties = new Properties();
                properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
                properties.setProperty("group.id","dispatcher_group");
                properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

                KafkaConsumer consumer = new KafkaConsumer<String, String>(properties);
                consumer.subscribe(Arrays.asList("send_message_response", "push_message"));

                System.out.println("分发系统的Kafka消费线程已经启动......");

                while (true) {
                    try {
                        ConsumerRecords<String, String> records = consumer.poll(100);

                        if(records == null || records.isEmpty()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        for (ConsumerRecord<String, String> record : records) {
                            JSONObject message = JSONObject.parseObject(record.value());
                            Integer requestType = message.getInteger("requestType");
                            long messageId = message.getLong("messageId");
                            long timestamp = message.getLong("timestamp");
                            String senderId = message.getString("senderId");
                            String receiverId = message.getString("receiverId");
                            String content = message.getString("content");

                            if(requestType.equals(Constants.REQUEST_TYPE_SEND_MESSAGE)) {
                                MessageSendResponseProto.MessageSendResponse.Builder builder =
                                        MessageSendResponseProto.MessageSendResponse.newBuilder();
                                builder.setMessageId(messageId);
                                builder.setTimestamp(timestamp);
                                builder.setSenderId(senderId);
                                builder.setReceiverId(receiverId);
                                MessageSendResponseProto.MessageSendResponse messageSendResponse =
                                        builder.build();

                                Response response = new Response(
                                        Constants.APP_SDK_VERSION_1,
                                        Constants.MESSAGE_TYPE_RESPONSE,
                                        Constants.REQUEST_TYPE_SEND_MESSAGE,
                                        Constants.SEQUENCE_DEFAULT,
                                        messageSendResponse.toByteArray()
                                );

                                String gatewayChannelId = message.getString("gatewayChannelId");
                                GatewayInstanceManager gatewayInstanceManager =
                                        GatewayInstanceManager.getInstance();
                                SocketChannel gatewayInstance = gatewayInstanceManager
                                        .getGatewayInstance(gatewayChannelId);

                                gatewayInstance.writeAndFlush(response.getBuffer());

                                System.out.println("收到单聊消息的响应：" + messageSendResponse +
                                        "，并且转发给接入系统");
                            } else if(requestType.equals(Constants.REQUEST_TYPE_PUSH_MESSAGE)) {
                                MessagePushRequestProto.MessagePushRequest.Builder builder =
                                        MessagePushRequestProto.MessagePushRequest.newBuilder();
                                builder.setMessageId(messageId);
                                builder.setTimestamp(timestamp);
                                builder.setSenderId(senderId);
                                builder.setReceiverId(receiverId);
                                builder.setContent(content);
                                MessagePushRequestProto.MessagePushRequest messagePushRequest =
                                        builder.build();

                                Request request = new Request(
                                        Constants.APP_SDK_VERSION_1,
                                        Constants.REQUEST_TYPE_PUSH_MESSAGE,
                                        Constants.SEQUENCE_DEFAULT,
                                        messagePushRequest.toByteArray()
                                );

                                JedisManager jedisManager = JedisManager.getInstance();
                                Jedis jedis = jedisManager.getJedis();
                                String sessionKey = "session_" + receiverId;
                                String session = jedis.get(sessionKey);

                                if(session != null) {
                                    JSONObject valueJson = JSONObject.parseObject(session);
                                    String gatewayChannelId = valueJson.getString("gatewayChannelId");

                                    GatewayInstanceManager gatewayInstanceManager =
                                            GatewayInstanceManager.getInstance();
                                    SocketChannel gatewayInstance = gatewayInstanceManager
                                            .getGatewayInstance(gatewayChannelId);

                                    gatewayInstance.writeAndFlush(request.getBuffer());

                                    System.out.println("转发消息推送给接入系统" +
                                            "（" + gatewayInstance + ")，" + messagePushRequest);
                                }
                            }
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
