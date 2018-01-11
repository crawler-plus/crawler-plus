package com.crawler;

import com.crawler.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class CrawlerSleuthServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerSleuthServerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CrawlerSleuthServerApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		LoggerUtils.printLogger(logger, "--------------sleuth-server project starts----------------");
	}
}
