package com.jp.equipment.controller;
import java.util.List;

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
	@ApiOperation("模拟推送kafka")
	public void sendKafka(@RequestBody List<LocationData> listLocation) {
		 sendKafkaService.sendKafka(listLocation);
	}
}
