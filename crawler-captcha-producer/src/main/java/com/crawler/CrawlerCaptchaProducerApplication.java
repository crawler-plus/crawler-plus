package com.crawler;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CrawlerCaptchaProducerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerCaptchaProducerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerCaptchaProducerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------CrawlerCaptchaProducer project starts----------------");
	}
}
