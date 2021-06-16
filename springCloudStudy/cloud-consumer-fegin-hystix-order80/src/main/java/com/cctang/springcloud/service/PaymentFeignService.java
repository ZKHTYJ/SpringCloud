package com.cctang.springcloud.service;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 23:41
 */
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTIX-PAYMENT", fallback = PaymentFeignServiceImpl.class )
/**
 * 查询单个
 * */
public interface PaymentFeignService {
    @GetMapping(value ="/payment/get/{id}")
    CommonResult<Payment> getPaymentforId(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/timeout")
    String paymentServiceTimeOut();

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);

}

