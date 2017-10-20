package com.crawler.components;

import com.crawler.service.api.MenuService;
import com.crawler.service.api.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * 启动容器以后，将数据库的sys_menu表中的系统管理和网站监控的host和port设置正确
 */
@Component
public class CrawlerCommandLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService menuService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(String... strings) throws Exception {
        if(logger.isWarnEnabled()) {
            logger.warn("serverAddress:" + serverAddress);
            logger.warn("serverPort:" + serverPort);
        }
        menuService.updateUrl("http://" + serverAddress + ":" + serverPort);
    }
}
