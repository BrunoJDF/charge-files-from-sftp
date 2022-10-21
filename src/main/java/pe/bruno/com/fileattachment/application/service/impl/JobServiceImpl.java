package pe.bruno.com.fileattachment.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.JobDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.persistence.model.JobSchedule;
import pe.bruno.com.fileattachment.persistence.repository.JobRepository;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository repository;
    private final MapperFacade mapperFacade;

    @Override
    public JobDto create(JobDto dto) {
        var toSave = mapperFacade.map(dto, JobSchedule.class);
        return Optional.of(repository.save(toSave))
                .map(jobSchedule -> this.mapperFacade.map(jobSchedule, JobDto.class))
                .orElseThrow(() -> new RuntimeException("error al crear"));
    }
}
