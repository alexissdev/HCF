package dev.notcacha.hcf.guice.modules;

import com.google.inject.AbstractModule;
import dev.notcacha.core.service.ServiceManager;
import dev.notcacha.hcf.guice.anotations.service.APIService;
import dev.notcacha.hcf.guice.anotations.service.HCFService;
import dev.notcacha.hcf.guice.anotations.service.SchedulerService;
import dev.notcacha.hcf.service.APIServiceManager;
import dev.notcacha.hcf.service.HCFServiceManager;
import dev.notcacha.hcf.service.SchedulerServiceManager;


public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(ServiceManager.class).annotatedWith(HCFService.class).to(HCFServiceManager.class);
        this.bind(ServiceManager.class).annotatedWith(SchedulerService.class).to(SchedulerServiceManager.class);
        this.bind(ServiceManager.class).annotatedWith(APIService.class).to(APIServiceManager.class);
    }
}
