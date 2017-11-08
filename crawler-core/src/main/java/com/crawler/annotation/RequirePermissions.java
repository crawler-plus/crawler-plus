package com.crawler.annotation;

import java.lang.annotation.*;

/**
 * 判断权限的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequirePermissions {
    public String[] value();
}
