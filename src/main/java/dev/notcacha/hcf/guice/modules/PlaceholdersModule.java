package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.hcf.guice.anotations.placeholders.EveryonePlaceholder;
import dev.notcacha.hcf.guice.anotations.placeholders.OresPlaceholder;
import dev.notcacha.hcf.guice.anotations.placeholders.StatisticsPlaceholder;
import dev.notcacha.hcf.placeholders.OresPlaceholderApplier;
import dev.notcacha.hcf.placeholders.PlaceholderApplier;
import dev.notcacha.hcf.placeholders.EveryonePlaceholderApplier;
import dev.notcacha.hcf.placeholders.StatisticsPlaceholderApplier;

public class PlaceholdersModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(PlaceholderApplier.class).annotatedWith(EveryonePlaceholder.class).to(EveryonePlaceholderApplier.class);
        this.bind(PlaceholderApplier.class).annotatedWith(StatisticsPlaceholder.class).to(StatisticsPlaceholderApplier.class);
        this.bind(PlaceholderApplier.class).annotatedWith(OresPlaceholder.class).to(OresPlaceholderApplier.class);
    }
}
