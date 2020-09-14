package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.service.APIServiceManager;
import dev.notcacha.hcf.service.HCFServiceManager;
import dev.notcacha.hcf.service.SchedulerServiceManager;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(ServiceManager.class).annotatedWith(Names.named("hcf-service")).to(HCFServiceManager.class);
        this.bind(ServiceManager.class).annotatedWith(Names.named("scheduler-service")).to(SchedulerServiceManager.class);
        this.bind(ServiceManager.class).annotatedWith(Names.named("api-service")).to(APIServiceManager.class);
    }
}
