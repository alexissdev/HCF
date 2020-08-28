package dev.notcacha.hcf.guice.anotations.service;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface SpawnService {
}
