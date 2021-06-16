package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 23:42
 */
@RestController
@Slf4j
// 全局处理降级方法
@DefaultProperties(defaultFallback = "globHystrixMethod")
public class OrderFeignHystixController {
    @Resource
    private PaymentFeignService paymentFeignService;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value ="/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        log.info("当前端口号port: " + serverPort );
        return paymentFeignService.getPaymentforId(id);
    };

    @GetMapping(value = "/consumer/payment/timeout")
    public String paymentServiceTimeOut(){
        // opefeign 一般默认等待一秒
        return paymentFeignService.paymentServiceTimeOut();
    }
    //成功
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
       return paymentFeignService.paymentInfo_OK(id);
    }


    //失败
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutFallback", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
//    })
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
       int age = 10/0;
        return paymentFeignService.paymentInfo_TimeOut(id);

    }

    public String paymentInfoTimeOutFallback(Integer id){
        return "我是消费者80，对方支付系统繁忙，请10秒钟之后再试，或者自己运行出错，请检查自己!/(ㄒoㄒ)/~~";
    }

    public String globHystrixMethod (){
        return "Glob方法。我是消费者80，对方支付系统繁忙，请10秒钟之后再试，或者自己运行出错，请检查自己!/(ㄒoㄒ)/~~";
    }
}
