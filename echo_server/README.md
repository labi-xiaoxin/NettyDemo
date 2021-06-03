# 这是一个应答服务器Demo

## 1.配置

### EchoServerHandler

服务端处理器主要重写了`channelRead()`方法，在接收到消息时，执行该方法。方法内主要实现了简单应答，即写入什么消息，应答什么消息。

### EchoServer

服务端服务主要通过配置引导器`ServerBootstrap`，引导器绑定端口。

## 2.运行

1. 启动`EchoServerApplication`类的Main方法
2. cmd命令行执行`telnet localhost 8081` 成功后-> 在命令行输入任意字符 -> 命令行返回一样的字符




