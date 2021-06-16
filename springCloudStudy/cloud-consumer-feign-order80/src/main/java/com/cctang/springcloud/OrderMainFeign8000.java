package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/30 19:40
 */
@SpringBootApplication
@EnableFeignClients
public class OrderMainFeign8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign8000.class,args);
    }
}
