package com.cctang.springcloud.service.impl;

import com.cctang.springcloud.service.IMessagePrivoder;
import lombok.val;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/6/22 22:56
 */
@EnableBinding(Source.class)
public class MessagePrivoder implements IMessagePrivoder {
    @Resource
    private MessageChannel output;
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("**********"+serial);
        return null;
    }
}
