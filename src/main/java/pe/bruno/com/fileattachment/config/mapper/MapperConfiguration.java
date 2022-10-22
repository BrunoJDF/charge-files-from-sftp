package pe.bruno.com.fileattachment.config.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Configuration;
import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;

import java.util.ArrayList;

@Configuration
public class MapperConfiguration {
    public void registerConfigurableMapper(DefaultMapperFactory defaultMapperFactory) {
        jobScheduleDtoToJobScheduleEntity(defaultMapperFactory);
    }

    void jobScheduleDtoToJobScheduleEntity(DefaultMapperFactory defaultMapperFactory) {
        defaultMapperFactory.classMap(CreateJobScheduleDto.class, JobScheduleEntity.class)
                .byDefault()
                .customize(new CustomMapper<CreateJobScheduleDto, JobScheduleEntity>() {
                    @Override
                    public void mapAtoB(CreateJobScheduleDto jobScheduleDto, JobScheduleEntity jobScheduleEntity, MappingContext context) {
                        var listParams = new ArrayList<JobParamEntity>();
                        jobScheduleEntity.setParams(listParams);
                    }
                })
                .register();
    }
}
