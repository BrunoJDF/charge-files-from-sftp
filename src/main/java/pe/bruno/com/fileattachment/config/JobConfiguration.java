package pe.bruno.com.fileattachment.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import pe.bruno.com.fileattachment.application.job.FileJob;

@Slf4j
@Configuration
@AllArgsConstructor
public class JobConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory createSpringJobFactory() {
        return new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                log.debug("job injection");
                var job = super.createJobInstance(bundle);
                applicationContext.getAutowireCapableBeanFactory()
                        .autowireBean(job);
                return job;
            }
        };
    }

    @Bean
    public SchedulerFactoryBean createSchedulerFactory(SpringBeanJobFactory springBeanJobFactory, Trigger trigger) {
        var schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setTriggers(trigger);

        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(springBeanJobFactory);
        return schedulerFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(FileJob.class);
        factoryBean.setDescription("Running JobDownloadFromSFTP...");
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setRepeatInterval(10000);
        trigger.setStartDelay(3000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return trigger;
    }


}
