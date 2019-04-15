package com.jp.equipment.runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jp.equipment.server.NettyServer;
/**
 * 
 * @author fengwei
 *
 */
@Component
public class NettyRunner implements CommandLineRunner {
	@Autowired
	private  NettyServer nettyServer;
	
	private static final Logger log = LoggerFactory.getLogger(NettyRunner.class);
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("开启netty服务");
		nettyServer.run();
		log.info("关闭netty服务");
	}

}
