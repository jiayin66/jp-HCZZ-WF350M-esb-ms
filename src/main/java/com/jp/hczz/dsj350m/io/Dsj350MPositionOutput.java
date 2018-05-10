package com.jp.hczz.dsj350m.io;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface Dsj350MPositionOutput {

    public static final String EXCHANGE_NAME = "ESB_WF_DSJ350M_POSITION";

    @Output(Dsj350MPositionOutput.EXCHANGE_NAME)
    public SubscribableChannel getDsj350MInfoChannel();

}
