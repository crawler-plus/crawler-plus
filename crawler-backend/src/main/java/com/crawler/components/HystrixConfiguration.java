package com.crawler.components;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

/**
 * Hystrix配置类
 */
@Configuration
@EnableCircuitBreaker
@EnableHystrixDashboard
public class HystrixConfiguration {
}
