package com.jp.hczz.dsj350m.netty.client;

import com.jp.hczz.dsj350m.event.MessageSendService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@ChannelHandler.Sharable
@Component
class ClientHandler extends ChannelHandlerAdapter {

    private Logger log = Logger.getLogger(ClientHandler.class);

    @Autowired
    private MessageSendService messageSendService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        try {
            String body = new String(req, "UTF-8");
            String[] msgs = body.split("@");
            if (msgs.length < 7) {
                log.warn("收到消息不符合协议格式忽略本条消息！！" + body);
                return;
            }
            if (!msgs[0].equals("1")) {
                log.warn("收到消息不是GPS报文消息，忽略本条消息！！" + body);
                return;
            }
            log.info("收到消息：" + body);
            messageSendService.sendMessage(body);
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
        log.warn("链接成功");
    }

    //与服务器断开连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("接受链接断开");
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭管道
        ctx.channel().close();
        log.error("netty发生异常~  " + cause);
        //打印异常信息
        cause.printStackTrace();
    }
}