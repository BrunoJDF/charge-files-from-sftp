package pe.bruno.com.fileattachment.persistence.repository.crud;

import org.springframework.data.repository.CrudRepository;
import pe.bruno.com.fileattachment.persistence.model.JobScheduleEntity;

import java.util.Optional;

public interface JobScheduleCrudRepository extends CrudRepository<JobScheduleEntity, Integer> {
    Optional<JobScheduleEntity> findByJobName(String jobName);
}
