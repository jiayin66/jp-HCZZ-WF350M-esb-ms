package com.jp.hczz.dsj350m.controller;

import com.jp.hczz.dsj350m.entity.Dsj350M;
import com.jp.hczz.dsj350m.service.Dsj350MDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloContriller {
    @Autowired
    private Dsj350MDaoService dsj350MDaoService;

    @GetMapping()
    public String test() {
        String msg = "1@130@273473@0@119.039204120636@36.6573214530945@26@0000@2018-05-08 18:21:09";
        Dsj350M dsj350M = new Dsj350M(msg);
        dsj350MDaoService.saveDsj350M(dsj350M);
        return "ok";
    }
}
