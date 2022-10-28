package pe.bruno.com.fileattachment.application.service;

import java.util.Optional;

public interface TokenService<T> {
    T save(T dto);

    Optional<T> findByIdAndActiveTrue(long id);

    T update(T toUpdate);
}
