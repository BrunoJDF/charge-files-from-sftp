package pe.bruno.com.fileattachment.persistence.repository;

import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;

import java.util.List;

public interface JobParamRepository {
    JobParamEntity save(JobParamEntity toSave);
    List<JobParamEntity> findAll();
    JobParamEntity findById(int id);
    boolean delete(int id);
}
