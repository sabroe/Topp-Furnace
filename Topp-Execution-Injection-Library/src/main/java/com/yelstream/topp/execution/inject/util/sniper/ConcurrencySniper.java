package com.yelstream.topp.execution.inject.util.sniper;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Initiates that there is to be kept a watchful eye on thread-related activities,
 * monitors the technical aspect of concurrency control.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-01
 */
@Inherited
@InterceptorBinding
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrencySniper {
    @Nonbinding
    String id() default "";

    @Nonbinding
    String comment() default "";

    @Nonbinding
    long warningAt() default 2;

    @Nonbinding
    long errorAt() default 2;
}
