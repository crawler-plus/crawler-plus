package com.crawler.components;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 开启缓存服务
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "crawler", name = "useEhCache", havingValue = "true")
public class EhCacheConfiguration {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager(bean.getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

}
