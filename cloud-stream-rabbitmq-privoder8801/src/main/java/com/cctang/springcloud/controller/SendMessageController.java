package com.cctang.springcloud.controller;

import com.cctang.springcloud.service.IMessagePrivoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/6/22 23:03
 */
@RestController
public class SendMessageController {
    @Resource
    private IMessagePrivoder messagePrivoder;
    @GetMapping(value = "/sendMessage")
    public String sendMessage(){
        return messagePrivoder.send();
    }
}
