package com.cctang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.text.html.HTMLDocument;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/25 22:58
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain8000.class,args);
    }
}
