package com.cctang.springcloud.controller;

import com.cctang.springcloud.entities.CommonResult;
import com.cctang.springcloud.entities.Payment;
import com.cctang.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/17 18:59
 */
@RestController
@ResponseBody
@Slf4j
public class OrderController {
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClint;
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
        return restTemplate.postForObject(PAYMENT_URL+"payment/modify",payment,CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/lb")
    public String getlb() {
        List<ServiceInstance> serviceInstance = discoveryClint.getInstances("CLOUD-PAYMENT-SERVICE");
        if (serviceInstance == null || serviceInstance.size()<0) {
            return null;
        }
        // 否则为有效服务
        ServiceInstance serviceInstances = loadBalancer.instance(serviceInstance);
        URI uri = serviceInstances.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    };
}
