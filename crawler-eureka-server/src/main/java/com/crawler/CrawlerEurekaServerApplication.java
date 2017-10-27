package com.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrawlerEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerEurekaServerApplication.class, args);
	}
}
