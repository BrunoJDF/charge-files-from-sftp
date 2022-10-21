package pe.bruno.com.fileattachment.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.bruno.com.fileattachment.persistence.model.JobSchedule;

@Repository
public interface FileRepository extends CrudRepository<JobSchedule, Integer> {
}
