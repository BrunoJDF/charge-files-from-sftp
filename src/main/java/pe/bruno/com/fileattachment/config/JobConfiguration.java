package pe.bruno.com.fileattachment.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import pe.bruno.com.fileattachment.application.job.FileJobProcess;

import java.util.Calendar;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    @Value("${config.job.cron-expression}")
    private String cronExpression;
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
    public SchedulerFactoryBean createSchedulerFactory(SpringBeanJobFactory springBeanJobFactory, Trigger... trigger) {
        var schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setTriggers(trigger);

        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(springBeanJobFactory);
        return schedulerFactory;
    }

    @Bean(name = "file")
    public JobDetailFactoryBean fileJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(FileJobProcess.class);
        factoryBean.setDescription("Running JobDownloadFromSFTP...");
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean(name = "fileTrigger")
    public CronTriggerFactoryBean fileTrigger(@Qualifier("file") JobDetail job) {
        log.info("cron expresion: " + cronExpression);
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(job);
        trigger.setStartTime(Calendar.getInstance().getTime());
        trigger.setCronExpression(cronExpression);
        trigger.setName("fileTrigger");
        return trigger;
    }


}
