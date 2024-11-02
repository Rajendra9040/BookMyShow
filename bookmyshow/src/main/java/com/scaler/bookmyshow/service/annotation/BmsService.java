package com.scaler.bookmyshow.service.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BmsService {

    @AliasFor(annotation = Service.class, attribute = "value")
    String qualifier() default "";

    String value() default "default";
}
