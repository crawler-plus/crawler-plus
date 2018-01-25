package com.crawler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class It4uThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(It4uThymeleafApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
