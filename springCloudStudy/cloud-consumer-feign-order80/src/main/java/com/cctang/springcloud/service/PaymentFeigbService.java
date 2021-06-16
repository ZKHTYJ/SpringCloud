package com.cctang.springcloud.service;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 19:43
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
/**
 * 查询单个
 * */
public interface PaymentFeigbService {
    @GetMapping(value ="/payment/get/{id}")
     CommonResult<Payment> getPaymentforId(@PathVariable("id") Long id);

     @GetMapping(value = "/payment/timeout")
       String paymentServiceTimeOut();

}

