package io.github.fajzu.shared.sector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SectorInitialize {

    SectorType[] type() default {
        SectorType.SPAWN,
        SectorType.NORMAL
    };
}