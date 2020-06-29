package com.zhss.im.business;

import com.alibaba.fastjson.JSONObject;
import com.zhss.im.common.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.sql.Date;
import java.util.Properties;

public class Application {

    private static JdbcTemplate jdbcTemplate;
    private static KafkaProducer kafkaProducer;

    static {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/im?characterEncoding=utf8&useSSL=true",
                "root",
                "root");
        Application.jdbcTemplate = new JdbcTemplate(dataSource);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Application.kafkaProducer = new KafkaProducer<String, String>(properties);
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("group.id","business_group");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Arrays.asList("send_message", "push_message_response"));

        System.out.println("业务逻辑系统已经启动，准备从Kafka消费消息......");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            if(records == null || records.isEmpty()) {
                Thread.sleep(100);
                continue;
            }

            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();

                JSONObject message = JSONObject.parseObject(record.value());

                if(topic.equals("send_message")) {
                    System.out.println("从Kafka中获取到一条单聊消息，" + record.value());
                    // 将数据写入MySQL中
                    long messageId = storeMessage(message);
                    // 返回消息发送的响应
                    sendMessageResponse(message, messageId);
                    // 进行消息推送
                    sendMessagePush(message, messageId);
                } else if(topic.equals("push_message_response")) {
                    processPushMessageResponse(message);
                }
            }
        }
    }

    /**
     * 处理推送消息的响应
     * @param message
     */
    private static void processPushMessageResponse(JSONObject message) {
        final long messageId = message.getLong("messageId");

        final String sql = "update message_receive set is_delivered=? where message_id=?";

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.NO_GENERATED_KEYS);
                statement.setInt(1, 1);
                statement.setLong(2, messageId);
                return statement;
            }
        });

        System.out.println("更新消息的投递状态，messageId=" + messageId);
    }

    /**
     * 发送消息推送
     * @param message
     */
    private static void sendMessagePush(JSONObject message, long messageId) {
        message.put("messageId", messageId);
        message.put("timestamp", System.currentTimeMillis());
        message.put("requestType", Constants.REQUEST_TYPE_PUSH_MESSAGE);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "push_message", message.toJSONString());

        kafkaProducer.send(record);

        System.out.println("将消息推送写入到Kafka中，" + message.toJSONString());
    }

    /**
     * 存储消息
     * @param messageJson
     */
    private static long storeMessage(JSONObject messageJson) {
        final String senderId = messageJson.getString("senderId");
        final String receiverId = messageJson.getString("receiverId");
        final String content = messageJson.getString("content");
        final Integer requestType = messageJson.getInteger("requestType");
        final Integer sequence = messageJson.getInteger("sequence");
        final Date sendTime = new Date(System.currentTimeMillis());

        final String insertMessageSendSql =
                "insert into message_send(" +
                    "sender_id," +
                    "receiver_id," +
                    "request_type," +
                    "sequence," +
                    "content," +
                    "send_time," +
                    "message_type) " +
                "VALUES(?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(
                        insertMessageSendSql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, senderId);
                statement.setString(2, receiverId);
                statement.setInt(3, requestType);
                statement.setInt(4, sequence);
                statement.setString(5, content);
                statement.setDate(6, sendTime);
                statement.setInt(7, 1);

                return statement;
            }
        }, keyHolder);

        final long messageId = keyHolder.getKey().longValue();

        System.out.println("将单聊消息写入message_send表，messageId=" + messageId);

        final String insertMessageReceiveSql =
                "insert into message_receive(" +
                        "message_id," +
                        "sender_id," +
                        "receiver_id," +
                        "request_type," +
                        "sequence," +
                        "content," +
                        "send_time," +
                        "message_type," +
                        "is_delivered) " +
                        "VALUES(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(
                        insertMessageReceiveSql, Statement.NO_GENERATED_KEYS);
                statement.setLong(1, messageId);
                statement.setString(2, senderId);
                statement.setString(3, receiverId);
                statement.setInt(4, requestType);
                statement.setInt(5, sequence);
                statement.setString(6, content);
                statement.setDate(7, sendTime);
                statement.setInt(8, 1);
                statement.setInt(9, 0);

                return statement;
            }
        });

        System.out.println("将单聊消息写入message_receive表......");

        return messageId;
    }

    /**
     * 发送单聊消息的响应
     * @param messageJson
     * @param messageId
     */
    private static void sendMessageResponse(
            JSONObject messageJson, long messageId) {
        String senderId = messageJson.getString("senderId");
        String receiverId = messageJson.getString("receiverId");
        Integer requestType = messageJson.getInteger("requestType");
        String gatewayChannelId = messageJson.getString("gatewayChannelId");

        JSONObject json = new JSONObject();
        json.put("senderId", senderId);
        json.put("receiverId", receiverId);
        json.put("messageId", messageId);
        json.put("timestamp", System.currentTimeMillis());
        json.put("gatewayChannelId", gatewayChannelId);
        json.put("requestType", requestType);

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "send_message_response", json.toJSONString());
        kafkaProducer.send(record);

        System.out.println("将单聊消息响应写入kafka中，" + json.toJSONString());
    }

}
