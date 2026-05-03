package io.github.fajzu.shared;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Schedule {

    long delay() default 0;

    long period() default 10L;

    boolean async() default true;
}