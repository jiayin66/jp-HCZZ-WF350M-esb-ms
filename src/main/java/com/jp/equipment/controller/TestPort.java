package com.jp.equipment.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "端口测试用接口")
public class TestPort {
	@Value("${server.port}")
	private Integer port;
	
	@Value("${nettyPort}")
	private Integer nettyPort;
	
    @PostMapping("/send")
    @ApiOperation("查看当前配置的netty端口号和本服务的端口号")
    public String sendMsg(){
        return "配置的netty端口号为："+nettyPort+"当前微服务的端口号为："+port;
    }
   
}
