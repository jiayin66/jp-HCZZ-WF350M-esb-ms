package com.jp.hczz.dsj350m.io;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 向location服务发送数据
 */
public interface CarPositionOutput {

    public static final String EXCHANGE_NAME = "ESB_CAR_POSITION";

    @Output(CarPositionOutput.EXCHANGE_NAME)
    public SubscribableChannel getCarInfoChannel();

}
