package com.jp.hczz.dsj350m.wsx350m.io;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface LocationOutput {
    String EXCHANGE_NAME = "ESB_LOCATION";

    @Output(LocationOutput.EXCHANGE_NAME)
    SubscribableChannel outputChannel();
}
