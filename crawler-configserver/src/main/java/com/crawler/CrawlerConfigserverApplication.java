package com.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CrawlerConfigserverApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerConfigserverApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerConfigserverApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		if(logger.isWarnEnabled()) {
			logger.warn("--------------springboot project starts----------------");
		}
	}
}
