package com.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		if(logger.isWarnEnabled()) {
			logger.warn("--------------crawler-backend project starts----------------");
		}
	}
}
