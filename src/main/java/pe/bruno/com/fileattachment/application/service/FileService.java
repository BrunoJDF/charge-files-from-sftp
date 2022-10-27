package pe.bruno.com.fileattachment.application.service;

import com.jcraft.jsch.SftpException;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.file.FolderDto;

import java.io.IOException;

public interface FileService {
    FolderDto downloadAction(String remoteFilePath);

    void getAllFilesAction(Object o, String remoteFilePath, String localFilePath) throws SftpException, IOException;

    int getFolderAction(String remoteFilePath, String localFilePath);

    FileDto save(FileDto create);

    FileDto update(FileDto update, String id);
}
