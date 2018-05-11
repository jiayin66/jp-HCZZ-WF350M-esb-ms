package com.jp.hczz.dsj350m.controller;

import com.jp.hczz.dsj350m.io.Dsj350MPositionOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@EnableBinding(Dsj350MPositionOutput.class)
public class HelloContriller {

    @Autowired
    private Dsj350MPositionOutput dsj350MPositionOutput;

    @GetMapping()
    public String test() {
//        String msg = "1@130@273473@0@119.039204120636@36.6573214530945@26@0000@2018-05-08 18:21:09";
//
//        System.out.println("Client said:" + msg);
//        Message<String> message = MessageBuilder.withPayload(msg).build();
//        dsj350MPositionOutput.getDsj350MInfoChannel().send(message);
        return "ok";
    }
}
