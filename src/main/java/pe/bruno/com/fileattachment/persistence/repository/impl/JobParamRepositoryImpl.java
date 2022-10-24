package pe.bruno.com.fileattachment.persistence.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;
import pe.bruno.com.fileattachment.persistence.repository.JobParamRepository;
import pe.bruno.com.fileattachment.persistence.repository.crud.JobParamCrudRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class JobParamRepositoryImpl implements JobParamRepository {
    private final JobParamCrudRepository crudRepository;

    @Override
    public JobParamEntity save(JobParamEntity toSave) {
        return crudRepository.save(toSave);
    }

    @Override
    public List<JobParamEntity> findAllByJobScheduleId(int jobId) {
        return this.crudRepository.findAllByJobScheduleId(jobId);
    }

    @Override
    public JobParamEntity findById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
