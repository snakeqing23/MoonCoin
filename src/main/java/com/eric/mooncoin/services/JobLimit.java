package com.eric.mooncoin.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tag the Job limitation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JobLimit {
    int max() default 1;
    int windowInSeconds() default 10;
}
