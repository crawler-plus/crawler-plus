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
public class CrawlerRedisServiceProviderApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerRedisServiceProviderApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerRedisServiceProviderApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------crawler-redis-service-provider project starts----------------");
	}
}
