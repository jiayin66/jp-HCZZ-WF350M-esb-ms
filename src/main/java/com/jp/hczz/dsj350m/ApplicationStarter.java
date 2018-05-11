package com.jp.hczz.dsj350m;

import com.jp.hczz.dsj350m.netty.NettyConfig;
import com.jp.hczz.dsj350m.netty.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan({"com.jp.hczz.dsj350m.entity", "com.jp.hczz.dsj350m.event.impl"})
public class ApplicationStarter extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private NettyClient client;

    @Autowired
    private NettyConfig nettyConfig;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationStarter.class);
    }

    @Override
    public void run(String... args) throws Exception {
        client.start(nettyConfig.getIp(), nettyConfig.getPort());
    }
}
