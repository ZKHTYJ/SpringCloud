package com.cctang.springcloud;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/7/9 13:32
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Skill88Main {

    public static void main(String[] args) {
        SpringApplication.run(Skill88Main.class, args);

    }
}
