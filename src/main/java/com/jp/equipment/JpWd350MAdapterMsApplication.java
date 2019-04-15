package com.jp.equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.jp.equipment.server.NettyServer;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages= {"com.jp.equipment"})
public class JpWd350MAdapterMsApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpWd350MAdapterMsApplication.class, args);
	}


}
