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

@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobScheduleRepository repository;
    private final JobParamRepository jobParamRepository;
    private final MapperFacade mapperFacade;

    @Override
    public JobScheduleDto create(CreateJobScheduleDto dto) {
        return Try.of(() -> this.mapperFacade.map(dto, JobScheduleEntity.class))
                .map(this.repository::save)
                .map(saved -> this.mapperFacade.map(saved, JobScheduleDto.class))
                .get();
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

    @Override
    public List<JobParamDto> getAllJobParamByJobScheduleId(int jobId) {
        return this.mapperFacade.mapAsList(jobParamRepository.findAllByJobScheduleId(jobId), JobParamDto.class);
    }

    @Override
    public JobScheduleDto getJobByJobName(String jobName) {
        return this.mapperFacade.map(this.repository.findByJobName(jobName), JobScheduleDto.class);
    }

    @Override
    public boolean deleteJobById(int id) {
        return this.repository.delete(id);
    }


}
