package pe.bruno.com.fileattachment.persistence.repository.crud;

import org.springframework.data.repository.CrudRepository;
import pe.bruno.com.fileattachment.persistence.model.JobParamEntity;

public interface JobParamCrudRepository extends CrudRepository<JobParamEntity, Integer> {
}
