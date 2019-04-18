package com.jp.equipment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jp.equipment.handler.WDRadioHandler;
import com.jp.equipment.io.LocationIO;
import com.jp.equipment.model.LocationData;
import com.jp.equipment.model.RadioMsg;
import com.jp.equipment.service.SendKafkaService;

import scala.Array;

/**
 * 
 * @author yanjiayin
 *
 */


@Service
@EnableBinding(LocationIO.class)
public class SendKafkaServiceImpl implements SendKafkaService{
	private static final Logger log = LoggerFactory.getLogger(SendKafkaServiceImpl.class);
	@Autowired
	private LocationIO locationIO;
	
	@Override
	public void sendKafka(List<LocationData> listLocation) {
		String locationJson = JSON.toJSONString(listLocation);
		log.debug("推送点位数据为："+locationJson);
		Message<String> message =MessageBuilder.withPayload(locationJson).build();
		locationIO.getOutputChannel().send(message);	
	}

}
