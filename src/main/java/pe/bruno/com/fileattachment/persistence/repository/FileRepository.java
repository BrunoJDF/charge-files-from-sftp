package pe.bruno.com.fileattachment.persistence.repository;


import pe.bruno.com.fileattachment.persistence.model.FileEntity;

public interface FileRepository {
    FileEntity save(FileEntity toSave);

    FileEntity findById(int id);

    boolean delete(int id);
}
