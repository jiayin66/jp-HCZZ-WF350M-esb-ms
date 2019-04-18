package com.jp.equipment.service;

import java.util.List;

import com.jp.equipment.model.LocationData;
import com.jp.equipment.model.RadioMsg;

public interface SendKafkaService {

	void sendKafka(List<LocationData> listLocation);

}
