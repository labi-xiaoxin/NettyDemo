# 基于流的传输的问题

基于流的传输如TCP/IP协议，接收到的数据存在socket的buffer中。它并不是一个数据包队列，而是字节队列。

**这意味着当发送两个独立的数据包时，操作系统也不会作为两个消息处理，而是一连串的字节。**

这就有可能造成远程写入的数据不会准确读取。

## 解决方案 1

方案1的思路如下：
    `构造一个内部的累积的缓冲，当完成指定数据量的收集时，才触发对数据的处理。`

### TimeClientHandler

1. 在TimeClientHandler中，定义了全局缓冲区：`private ByteBuf byteBuf`
2. 重写两个ChannelHandler生命周期的监听方法，分别实现初始化全局缓冲，释放清空全局缓冲。
3. 在`channelRead()`方法中，对全局缓冲区大于等于4时进行数据处理。

## 优化解决方案 2

解决方案1已经解决了流传输的问题，但不够友好，一旦有多个字段组成的复杂协议，这样的解决方案变得难以维护。

 方案2的思路如下：
    `把一整个ChannelHandler拆分为多个模块，每个模块的处理各不相同！`
 
### TimeClientDecoderHandler

该类专门处理缓冲区数据。继承`ByteToMessageDecoder`，重写`decode`方法。

缓冲区数据小于4字节时，不进行任何处理，一直读。超过4字节时，丢弃读取过的数据。