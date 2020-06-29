# im-netty
基于netty开发一个即时通讯系统

### 第一次提交

1、打通了接入系统和app sdk的双向通信

2、如何建立长连接：接入层会缓存跟客户端的长连接，可以随时互发消息

3、授权认证模拟代码，和普通消息一样的形势发送，只是开头为字符串常量“发起用户认证”

```java
byte[] messageBytes = ("发起用户认证|" + userId + "|" + token + "$_").getBytes();
```

4、接入系统的有个线程，一直推送消息（模拟一个用户客户端），根据userId可以找到对应的channel。

5、粘包问题的处理

```java
//发送token认证请求
byte[] messageBytes = ("发起用户认证|" + userId + "|" + token + "$_").getBytes();
//发送消息
byte[] messageBytes = (message + "|" + userId + "$_").getBytes();
```

如上面代码所示，在发送信息或回复信息的末尾加上符号"$_"（也可以定义其他符号）。

然后在app sdk和接入系统两边的channel的pipeline中都加上以下handler处理逻辑

```java
ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
socketChannel.pipeline().addLast(new StringDecoder());     //会把收到的消息直接转为String，而不是ByteBuf
```

### 第二次提交

1、引入分发系统

2、引入protobuf

3、接入系统和所有分发系统通过netty建立连接（通过netty）

### 第三次提交

1、实现了app sdk、接入系统、分发系统的双向通信

2、认证流程

3、增加了一个common工程

### 第四次提交

1、加入了redis、mysql的jdbc、kafka的示例操作代码

2、分发系统宕机

接入系统维护了一个所有分发系统的map，分发系统宕机，直接在map中把对应实例删掉就好。

3、接入系统宕机

1）分发系统感知到，会在维护的实例列表中把对应实例删掉。

2）app sdk感知到，会重新选一台接入系统去连接。（会重新认证）

4、app sdk宕机

1）接入系统感知到，会把本地和redis中的session都删掉

### 第五次提交

1、接入系统将请求数据转换成json放到kafka中。

2、jdbc插入数据时如何返回自增组件，需要注意。

### 第六次提交

1、完成了单聊消息的全流程

2、流程如下：用户u1（假设他要发给u2）调用app sdk发送消息到接入系统，接入系统发送消息到分发系统，分发系统发送消息到kafka，业务系统去kafka中消费，将单聊消息存到redis和mysql中。然后以同样的流程响应回复给u1。

然后业务系统将消息发给kafka，分发系统消费到消息，发给接入系统，接入系统发给用户u1对应的app sdk。然后同样的流程返回响应到kafka并更新mysql。