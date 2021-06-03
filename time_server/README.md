# 这是一个时间服务器Demo

## 1. 服务端

### TimeServerHandler

服务端处理器主要重写了`channelActive()`方法，在连接上服务端时将会调用该方法。方法内主要实现了写入当前时间的时间戳[int]

### TimeServer

服务端服务主要通过配置引导器`ServerBootstrap`，引导器绑定端口。

## 2. 客户端

### TimeClientHandler

客户端处理器主要重写了`channelRead()`方法，在服务器返回数据时调用该方法。方法内主要实现了对服务器返回的时间戳[int]进行读取，并输出为日期格式。

### TimeClient

客户端服务主要通过配置引导器`Bootstrap`，引导器执行`connect(host,port)`，连接地址与端口，然后关闭。

## 3. 运行

1. 启动`TimeServerApplication`类的Main方法，控制台显示：`服务端已启动`
2. 启动`TimeClientApplication`类的Main方法，控制台显示：`Thu Jun 03 11:51:31 CST 2021`格式类似
3. 此时`TimeServerApplication`类的控制台，多一行：`1622692291`
4. 以上顺序很重要！服务端启动->客户端连接！