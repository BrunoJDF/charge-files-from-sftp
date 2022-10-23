package pe.bruno.com.fileattachment.config.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Configuration;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;

@Configuration
public class MapperConfiguration {
    public void registerConfigurableMapper(DefaultMapperFactory defaultMapperFactory) {
        jobScheduleDtoToJobScheduleEntity(defaultMapperFactory);
    }

    void jobScheduleDtoToJobScheduleEntity(DefaultMapperFactory defaultMapperFactory) {
        defaultMapperFactory.classMap(JobScheduleDto.class, JobScheduleEntity.class)
                .byDefault()
                .customize(new CustomMapper<JobScheduleDto, JobScheduleEntity>() {
                    @Override
                    public void mapAtoB(JobScheduleDto jobScheduleDto, JobScheduleEntity jobScheduleEntity, MappingContext context) {
                        if(!jobScheduleDto.getParams().isEmpty()) {

                        }
                    }
                })
                .register();
    }
}
