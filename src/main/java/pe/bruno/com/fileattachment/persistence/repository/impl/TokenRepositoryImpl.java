package pe.bruno.com.fileattachment.persistence.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.bruno.com.fileattachment.persistence.crud.TokenCrudRepository;
import pe.bruno.com.fileattachment.persistence.entity.TokenEntity;
import pe.bruno.com.fileattachment.persistence.repository.EntityRepository;
import pe.bruno.com.fileattachment.web.exception.BadRequestException;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements EntityRepository<TokenEntity> {
    private final TokenCrudRepository crudRepository;

    @Override
    public TokenEntity save(TokenEntity toSave) {
        return crudRepository.save(toSave);
    }

    @Override
    public Optional<TokenEntity> findByIdAndActiveTrue(long id) {
        return crudRepository.findByIdAndActiveIsTrue(id);
    }

    @Override
    public TokenEntity update(TokenEntity toUpdate) {
        if(toUpdate.getId() != 0){
            return crudRepository.save(toUpdate);
        } else {
            throw new BadRequestException("Error al actualizar");
        }
    }
}
