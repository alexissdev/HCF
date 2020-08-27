package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.hcf.guice.anotations.placeholders.SimplePlaceholders;
import dev.notcacha.hcf.placeholders.PlaceholderApplier;
import dev.notcacha.hcf.placeholders.SimplePlaceholderApplier;

public class PlaceholdersModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(PlaceholderApplier.class).annotatedWith(SimplePlaceholders.class).to(SimplePlaceholderApplier.class);
    }
}
