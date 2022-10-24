package pe.bruno.com.fileattachment.persistence.repository.crud;

import org.springframework.data.repository.CrudRepository;
import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;

import java.util.List;

public interface JobParamCrudRepository extends CrudRepository<JobParamEntity, Integer> {
    List<JobParamEntity> findAllByJobScheduleId(int jobId);
}
