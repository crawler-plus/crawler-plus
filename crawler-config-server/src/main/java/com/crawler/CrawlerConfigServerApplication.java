package com.crawler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CrawlerConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerConfigServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}