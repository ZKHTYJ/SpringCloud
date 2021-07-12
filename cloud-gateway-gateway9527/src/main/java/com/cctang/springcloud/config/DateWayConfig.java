package com.cctang.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/6/8 21:58
 */
@Configuration
public class DateWayConfig {
    @Bean
    public RouteLocator custormRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_cctang", r ->r.path("/bilibili").uri("https://fanyi.youdao.com/")).build();
        return routes.build();
    }
}
