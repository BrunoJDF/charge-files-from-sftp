package pe.bruno.com.fileattachment.application.service.impl;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;
import pe.bruno.com.fileattachment.persistence.repository.JobParamRepository;
import pe.bruno.com.fileattachment.persistence.repository.JobScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobScheduleRepository repository;
    private final JobParamRepository jobParamRepository;
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
        return this.mapperFacade.map(repository.findById(id), JobScheduleDto.class);
    }

    @Override
    public List<JobScheduleDto> getAllJob() {
        return this.mapperFacade.mapAsList(repository.findAll(), JobScheduleDto.class);
    }

    @Override
    public JobScheduleDto addJobParam(JobParamDto param, int id) {

        var result = Try.of(() -> this.repository.findById(id))
                .map(founded -> {
                    var paramEntity = this.mapperFacade.map(param, JobParamEntity.class);
                    paramEntity.setJobSchedule(founded);
                    return this.jobParamRepository.save(paramEntity);
                })
                .map(paramSaved -> this.repository.findById(id)).get();
        log.info("Response " + result);
        return this.mapperFacade.map(result, JobScheduleDto.class);
    }


}
