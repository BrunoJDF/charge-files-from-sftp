package pe.bruno.com.fileattachment.persistence.repository;

import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;

import java.util.List;

public interface JobScheduleRepository {

    JobScheduleEntity save(JobScheduleEntity toSave);
    List<JobScheduleEntity> findAll();
    JobScheduleEntity findById(int id);
    boolean delete(int id);
}
