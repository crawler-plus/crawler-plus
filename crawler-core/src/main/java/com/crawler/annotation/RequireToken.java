package com.crawler.annotation;

import java.lang.annotation.*;

/**
 * 需要token验证的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequireToken {
}
