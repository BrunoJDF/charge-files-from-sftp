package pe.bruno.com.fileattachment.config.mapper;

import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    public void registerConfigurableMapper(DefaultMapperFactory defaultMapperFactory) {
        jobScheduleDtoToJobScheduleEntity(defaultMapperFactory);
    }

    void jobScheduleDtoToJobScheduleEntity(DefaultMapperFactory defaultMapperFactory) {
    }
}
