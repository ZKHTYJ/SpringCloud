package com.cctang.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/6/17 23:01
 */
@RestController
@RefreshScope
// cmd -> 运维人员运行 curl -X POST "http://localhost:3355/actuator/refresh" 3355才能正式生效 比重启3355好很多
//curl -X POST "http://localhost:3344/actuator/bus-refresh"
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}

