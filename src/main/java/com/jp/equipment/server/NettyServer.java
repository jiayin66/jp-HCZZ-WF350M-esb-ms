package com.jp.equipment.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.jp.equipment.handler.YunTuHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 
 * @author fengwei
 * 修改于20190402 鄢家银
 */
@Component
public class NettyServer implements Runnable {
	
	@Value("${nettyPort}")
	private Integer nettyPort;
	
	@Autowired
	private YunTuHandler yunTuHandler;


	
	@Override
	public void run() {
		  EventLoopGroup bossGroup=new NioEventLoopGroup();
	        try
	        {
	            //通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
	            //UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
	            Bootstrap b=new Bootstrap();
	            b.group(bossGroup)
	            .channel(NioDatagramChannel.class)
	            .option(ChannelOption.SO_BROADCAST, true)
	            .handler(yunTuHandler);
	            b.bind(nettyPort).sync().channel().closeFuture().await();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally{
	            bossGroup.shutdownGracefully();
	        }
	    }



	
}
