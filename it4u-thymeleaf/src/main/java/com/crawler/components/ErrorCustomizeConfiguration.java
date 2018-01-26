package com.crawler.components;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorCustomizeConfiguration {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/404.html");
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/404.html");
            container.addErrorPages(errorPage400, errorPage404, errorPage500);
        };
    }

}
