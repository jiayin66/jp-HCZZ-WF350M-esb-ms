package com.jp.equipment.server;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.jp.equipment.handler.RadioMsgDecoder;
import com.jp.equipment.handler.WDRadioHandler;
import com.jp.equipment.handler.YunTuHandler;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
/**
 * 
 * @author fengwei
 * 修改于20190402 鄢家银
 */
@Component
public class NettyServer implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
	
	@Value("${nettyPort}")
	private Integer nettyPort;
	
	@Autowired
	private RadioMsgDecoder radioMsgDecoder;
	@Autowired
	private YunTuHandler yunTuHandler;
	@Autowired
	private WDRadioHandler wDRadioHandler;

/*	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.debug("start.....");
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(mainGroup, workerGroup).channel(NioServerSocketChannel.class)
			//.option(ChannelOption.SO_BACKLOG, 1024)
					//.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
					.option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(radioMsgDecoder);
							ch.pipeline().addLast(wDRadioHandler);
						}
					});

			ChannelFuture f = server.bind(nettyPort).sync();
			log.debug(WDRadioHandler.class.getName() + "started and listen on " + f.channel().localAddress());
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			log.info("报文解析出错");
			log.error(e.getMessage());
		} finally {
			try {
				mainGroup.shutdownGracefully().sync();
				workerGroup.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}

		}

	}*/
	
	
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
