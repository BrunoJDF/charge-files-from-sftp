package pe.bruno.com.fileattachment.persistence.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;
import pe.bruno.com.fileattachment.persistence.repository.JobScheduleRepository;
import pe.bruno.com.fileattachment.persistence.repository.crud.JobScheduleCrudRepository;
import pe.bruno.com.fileattachment.web.exception.NotFoundException;

import java.util.List;

@Repository
@AllArgsConstructor
public class JobScheduleRepositoryImpl implements JobScheduleRepository {

    private final JobScheduleCrudRepository crudRepository;
    private final String JOB_NOT_FOUND = "Job not found";

    @Override
    public JobScheduleEntity save(JobScheduleEntity toSave) {
        return crudRepository.save(toSave);
    }

    @Override
    public List<JobScheduleEntity> findAll() {
        return (List<JobScheduleEntity>) crudRepository.findAll();
    }

    @Override
    public JobScheduleEntity findById(int id) {
        return crudRepository.findById(id).orElseThrow(() -> new NotFoundException(JOB_NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.findById(id)
                .map(toDelete -> {
                    this.crudRepository.delete(toDelete);
                    return true;
                })
                .orElseThrow(() -> new NotFoundException(JOB_NOT_FOUND));
    }

    @Override
    public JobScheduleEntity findByJobName(String jobName) {
        return crudRepository.findByJobName(jobName).orElseThrow(() -> new NotFoundException(JOB_NOT_FOUND));
    }
}
