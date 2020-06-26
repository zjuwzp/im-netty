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