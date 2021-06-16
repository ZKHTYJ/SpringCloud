package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/24 23:17
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class PaymentMainZK8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMainZK8004.class,args);
    }
}
