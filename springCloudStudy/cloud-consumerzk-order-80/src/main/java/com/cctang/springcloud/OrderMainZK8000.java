package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/25 20:39
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainZK8000 {
        public static void main(String[] args) {
            SpringApplication.run(com.cctang.springcloud.OrderMainZK8000.class,args);
        }
}
