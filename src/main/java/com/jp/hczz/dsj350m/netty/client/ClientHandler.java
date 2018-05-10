package com.jp.hczz.dsj350m.netty.client;

import com.jp.hczz.dsj350m.io.Dsj350MPositionOutput;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.UnsupportedEncodingException;

class ClientHandler extends ChannelHandlerAdapter {

    @Autowired
    private Dsj350MPositionOutput dsj350MPositionOutput;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        try {
            String body = new String(req, "UTF-8");
            System.out.println("Client said:" + body);
            Message<String> message = MessageBuilder.withPayload(body).build();
            dsj350MPositionOutput.getDsj350MInfoChannel().send(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    //与服务器建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive");
    }

    //与服务器断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭管道
        ctx.channel().close();
        //打印异常信息
        cause.printStackTrace();
    }
}