package com.cctang.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/5/25 20:44
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
    // 负载均衡
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}