package com.jp.hczz.350m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories("com.jp.hczz.350m.dao")
@EntityScan("com.jp.hczz.350m.entity")
public class ApplicationStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}
}
