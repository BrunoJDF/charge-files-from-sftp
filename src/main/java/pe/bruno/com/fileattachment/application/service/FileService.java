package pe.bruno.com.fileattachment.application.service;

import com.jcraft.jsch.ChannelSftp;
import pe.bruno.com.fileattachment.application.dto.FileDto;
import pe.bruno.com.fileattachment.application.dto.FolderDto;

public interface FileService {
    FolderDto downloadFile(String remoteFilePath);

    void getAllFilesAction(Object o, ChannelSftp channelSftp, String remoteFilePath);

    FileDto downloadFile(String remoteFilePath, String localFilePath);

    FileDto save(FileDto create);

    FileDto update(FileDto update, String id);
}
