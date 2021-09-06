package com.cctang.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:16
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
public class TransactionMain1994 {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMain1994.class,args);
    }
}
