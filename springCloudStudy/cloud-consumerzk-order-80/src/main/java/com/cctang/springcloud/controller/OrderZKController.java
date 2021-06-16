package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/25 20:45
 */
@RestController
@ResponseBody
@Slf4j
public class OrderZKController {
    public static final String PAYMENT_URL = "http://cloud-payment-service";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
    }
    @GetMapping(value ="/consumer/payment/get/{id}")
    public CommonResult getPaymentById (@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/get/all")
    public CommonResult getPaymentAll(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/all",CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/del/{id}")
    public CommonResult delPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/del"+id,CommonResult.class);

    }
    @GetMapping(value = "/consumer/payment/modify")
    public CommonResult modifyPaymentById(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/modify",payment,CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/zk")
    public String getZKPayment() {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/zk",String.class);
    }
}
