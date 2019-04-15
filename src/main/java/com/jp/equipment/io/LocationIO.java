package com.jp.equipment.io;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface LocationIO {
	public static final String ESBLOCATION = "ESB_LOCATION";
	//  public static final String ESBLOCATION = "ESB_CAR_POSITION";

	    @Output(ESBLOCATION)
	    public SubscribableChannel getOutputChannel();
	    
}
