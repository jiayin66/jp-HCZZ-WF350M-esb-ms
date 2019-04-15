package com.jp.equipment.service;

import com.jp.equipment.model.LocationData;
import com.jp.equipment.model.RadioMsg;

public interface SendKafkaService {

	LocationData sendKafka(RadioMsg radioMsg);

}
