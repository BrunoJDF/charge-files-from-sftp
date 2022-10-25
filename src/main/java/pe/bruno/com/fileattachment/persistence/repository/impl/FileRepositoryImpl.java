package pe.bruno.com.fileattachment.persistence.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.bruno.com.fileattachment.persistence.model.FileEntity;
import pe.bruno.com.fileattachment.persistence.repository.FileRepository;
import pe.bruno.com.fileattachment.persistence.repository.crud.FileCrudRepository;
import pe.bruno.com.fileattachment.web.exception.NotFoundException;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final FileCrudRepository crudRepository;
    private final static String FILE_NOT_FOUND = "File not found";

    @Override
    public FileEntity save(FileEntity toSave) {
        return crudRepository.save(toSave);
    }

    @Override
    public FileEntity findById(int id) {
        return crudRepository.findById(id).orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.findById(id)
                .map(toDelete -> {
                    crudRepository.delete(toDelete);
                    return true;
                })
                .orElseThrow(() -> new NotFoundException(FILE_NOT_FOUND));
    }
}
