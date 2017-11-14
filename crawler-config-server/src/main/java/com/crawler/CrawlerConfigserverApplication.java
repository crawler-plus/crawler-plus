package com.crawler;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CrawlerConfigServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerConfigServerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerConfigServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------crawler-config-server project starts----------------");
	}
}
