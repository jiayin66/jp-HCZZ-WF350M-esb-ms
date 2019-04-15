package com.jp.equipment.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jp.equipment.model.LocationData;
import com.jp.equipment.model.RadioMsg;
import com.jp.equipment.service.SendKafkaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/shoutai")
@Api(tags = "发送到卡夫卡")
public class SendKafkaController {
	@Autowired
	private SendKafkaService sendKafkaService;
	@PostMapping("/send")
	@ApiOperation("根据解析的数据，推送卡夫卡： radioCode:71821451 ，radioNS：3723.2475，radioTime:161229.487，radionEW:12158.3416")
	public LocationData sendKafka(@RequestBody RadioMsg radioMsg) {
		return sendKafkaService.sendKafka(radioMsg);
	}
}
