package com.crawler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrawlerEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerEurekaServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
