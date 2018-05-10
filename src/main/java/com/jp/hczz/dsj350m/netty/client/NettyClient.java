package com.jp.hczz.dsj350m.netty.client;

import com.jp.hczz.dsj350m.event.MessageSendService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

@Component
public class NettyClient {

    @Autowired
    private ClientHandler clientHandler;

    private final static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public void start(String host, int port) {
        System.out.println("启动服务" + host + ":" + port);
        // EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
        EventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap(); // 客户端引导类
        bootstrap.group(nioEventLoopGroup);// 多线程处理
        bootstrap.channel(NioSocketChannel.class);// 指定通道类型为NioServerSocketChannel，一种异步模式，OIO阻塞模式为OioServerSocketChannel
//        bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535));
        bootstrap.remoteAddress(new InetSocketAddress(host, port));// 指定请求地址

        try {
            synchronized (bootstrap) {
                //设置管道工厂
                bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //获取管道
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addFirst(new ChannelHandlerAdapter() {
                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                super.channelInactive(ctx);
                                System.out.println("重连");
                                ctx.channel().eventLoop().schedule(() -> doConnect(bootstrap), 1, TimeUnit.SECONDS);
                            }
                        });
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        //处理类
                        pipeline.addLast(clientHandler);
                    }
                });
            }
            doConnect(bootstrap);
        } catch (Exception e) {
            logger.error("exception happends e {}", e);
        }
    }

    private void doConnect(Bootstrap bootstrap) {
        if (closed) {
            return;
        }
        ChannelFuture future = bootstrap.connect();
        future.addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                System.out.println("连接 成功");
            } else {
                System.out.println("重连");
                f.channel().eventLoop().schedule(() -> doConnect(bootstrap), 1, TimeUnit.SECONDS);
            }
        });
    }

    Boolean closed = false;

    public void close() {
        closed = true;
//        workerGroup.shutdownGracefully();
    }
}
