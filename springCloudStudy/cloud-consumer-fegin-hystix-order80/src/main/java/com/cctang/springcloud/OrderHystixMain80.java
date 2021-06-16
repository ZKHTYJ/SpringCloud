package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 23:38
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderHystixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystixMain80.class,args);
    }
}
