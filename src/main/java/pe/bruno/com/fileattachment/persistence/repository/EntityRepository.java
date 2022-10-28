package pe.bruno.com.fileattachment.persistence.repository;

import java.util.Optional;

public interface EntityRepository<T> {
    T save(T toSave);
    Optional<T> findByIdAndActiveTrue(long id);

    T update(T toUpdate);
}
