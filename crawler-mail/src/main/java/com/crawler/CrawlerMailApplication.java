package com.crawler;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CrawlerMailApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerMailApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerMailApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------crawler-mail project starts----------------");
	}
}
