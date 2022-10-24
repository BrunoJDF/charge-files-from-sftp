package pe.bruno.com.fileattachment.application.service;

import com.jcraft.jsch.ChannelSftp;
import pe.bruno.com.fileattachment.application.dto.FileResponseDto;

public interface FileService {
    FileResponseDto downloadFile(String remoteFilePath);

    void getAllFilesAction(Object o, ChannelSftp channelSftp, String remoteFilePath);

    void downloadFile(String remoteFilePath, String localFilePath);
}
