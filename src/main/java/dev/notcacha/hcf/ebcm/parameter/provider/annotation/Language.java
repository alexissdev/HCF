package dev.notcacha.hcf.ebcm.parameter.provider.annotation;

import dev.notcacha.hcf.ebcm.parameter.provider.LanguageProvider;
import me.fixeddev.ebcm.parametric.annotation.ModifierAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ModifierAnnotation(LanguageProvider.LANGUAGE_MODIFIER)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Language {
}
