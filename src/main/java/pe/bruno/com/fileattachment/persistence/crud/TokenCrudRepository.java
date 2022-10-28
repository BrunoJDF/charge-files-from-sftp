package pe.bruno.com.fileattachment.persistence.crud;

import org.springframework.data.repository.CrudRepository;
import pe.bruno.com.fileattachment.persistence.entity.TokenEntity;

import java.util.Optional;

public interface TokenCrudRepository extends CrudRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByIdAndActiveIsTrue(long id);
}
