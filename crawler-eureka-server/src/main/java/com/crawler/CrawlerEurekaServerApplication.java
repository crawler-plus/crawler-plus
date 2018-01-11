package com.crawler;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrawlerEurekaServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerEurekaServerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerEurekaServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------crawler-eureka-server project starts----------------");
	}
}
