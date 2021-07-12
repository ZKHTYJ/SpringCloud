package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:32
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Skill88Main {
    public static void main(String[] args) {
        SpringApplication.run(Skill88Main.class,args);
    }
}
