package dev.notcacha.hcf.guice.anotations.loader;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface FactionsLoader {
}
