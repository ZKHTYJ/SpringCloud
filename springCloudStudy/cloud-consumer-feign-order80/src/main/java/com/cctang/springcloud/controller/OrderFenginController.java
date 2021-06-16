package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.service.PaymentFeigbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 19:53
 */
@RestController
@Slf4j
public class OrderFenginController {
    @Resource
    private PaymentFeigbService paymentFeigbService;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value ="/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("当前端口号port: " + serverPort );
        return paymentFeigbService.getPaymentforId(id);
    };

    @GetMapping(value = "/consumer/payment/timeout")
    public String paymentServiceTimeOut(){
        // opefeign 一般默认等待一秒
        return paymentFeigbService.paymentServiceTimeOut();
    }
}
