package pe.bruno.com.fileattachment.persistence.repository.crud;

import org.springframework.data.repository.CrudRepository;
import pe.bruno.com.fileattachment.persistence.model.FileEntity;

public interface FileCrudRepository extends CrudRepository<FileEntity, Integer> {
}
