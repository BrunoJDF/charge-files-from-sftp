package pe.bruno.com.fileattachment.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;
import pe.bruno.com.fileattachment.persistence.repository.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository repository;
    private final MapperFacade mapperFacade;

    @Override
    public JobScheduleDto create(CreateJobScheduleDto dto) {
        var toSave = mapperFacade.map(dto, JobScheduleEntity.class);
        return Optional.of(repository.save(toSave))
                .map(jobScheduleEntity -> this.mapperFacade.map(jobScheduleEntity, JobScheduleDto.class))
                .orElseThrow(() -> new RuntimeException("error al crear"));
    }

    @Override
    public JobScheduleDto getJob(int id) {
        return repository.findById(id)
                .map(jobScheduleEntity -> this.mapperFacade.map(jobScheduleEntity, JobScheduleDto.class))
                .orElseThrow(() -> new RuntimeException("error al obtener"));
    }

    @Override
    public List<JobScheduleDto> getAllJob() {
        return this.mapperFacade.mapAsList(repository.findAll(), JobScheduleDto.class);
    }


}
