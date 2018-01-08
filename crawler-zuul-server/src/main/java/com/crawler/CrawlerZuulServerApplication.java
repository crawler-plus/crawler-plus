package com.crawler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CrawlerZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerZuulServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
