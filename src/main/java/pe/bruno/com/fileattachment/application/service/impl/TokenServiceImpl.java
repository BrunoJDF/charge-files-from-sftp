package pe.bruno.com.fileattachment.application.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.tieto.TokenDto;
import pe.bruno.com.fileattachment.application.service.TokenService;
import pe.bruno.com.fileattachment.persistence.entity.TokenEntity;
import pe.bruno.com.fileattachment.persistence.repository.EntityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService<TokenDto> {
    private final EntityRepository<TokenEntity> repository;
    private final MapperFacade mapper;

    @Override
    public TokenDto save(TokenDto dto) {
        TokenEntity toSave = mapper.map(dto, TokenEntity.class);
        TokenEntity saved = repository.save(toSave);
        return mapper.map(saved, TokenDto.class);
    }

    @Override
    public Optional<TokenDto> findByIdAndActiveTrue(long id) {
        return repository.findByIdAndActiveTrue(id)
                .map(tokenEntity -> mapper.map(tokenEntity, TokenDto.class));
    }

    @Override
    public TokenDto update(TokenDto toUpdate) {
        TokenEntity toSave = mapper.map(toUpdate, TokenEntity.class);
        TokenEntity updated = repository.update(toSave);
        return mapper.map(updated, TokenDto.class);
    }
}

