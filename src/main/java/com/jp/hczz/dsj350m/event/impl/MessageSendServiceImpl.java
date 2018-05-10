package com.jp.hczz.dsj350m.event.impl;

import com.jp.common.utils.serialize.SerializeUtil;
import com.jp.hczz.dsj350m.event.MessageSendService;
import com.jp.hczz.dsj350m.io.CarPositionOutput;
import com.jp.hczz.dsj350m.io.Dsj350MPositionOutput;
import com.jp.hczz.dsj350m.model.TResLocationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@EnableBinding({Dsj350MPositionOutput.class, CarPositionOutput.class})
public class MessageSendServiceImpl implements MessageSendService {
    private Logger log = Logger.getLogger(Dsj350MPositionOutput.class);
    @Autowired
    Dsj350MPositionOutput dsj350MPositionOutput;

    @Autowired
    CarPositionOutput carPositionOutput;

    @Override
    public void sendMessage(String msg) {
        System.out.println("向kafka 发送数据 topic:" + msg);
        Message<String> message = MessageBuilder.withPayload(msg).build();
        boolean flag = dsj350MPositionOutput.getDsj350MInfoChannel().send(message);
        if (flag) {
            log.info("成功向kafka（ESB_WF_DSJ350M_POSITION）推送数据:" + msg);
        }
        TResLocationData tResLocationData = new TResLocationData(msg);
        String carPosition = SerializeUtil.toJson(tResLocationData);
        log.info("carPosition:" + carPosition);
        message = MessageBuilder.withPayload(carPosition).build();
//        flag = carPositionOutput.getCarInfoChannel().send(message);
        if (flag) {
            log.info("成功向kafka（ESB_CAR_POSITION）推送数据:" + carPosition);
        }

    }
}
