package pe.bruno.com.fileattachment.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.config.SftpConfiguration;
import pe.bruno.com.fileattachment.model.SftpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

@Service
@AllArgsConstructor
@Slf4j
public class SftpService {
    private final SftpConfiguration configuration;

    public SftpResponse downloadFile(String remoteFilePath) {
        ChannelSftp channelSftp = configuration.createChannelSftp();
        try {
            Vector<?> fileList = channelSftp.ls(remoteFilePath);
            if (fileList.size() > 0) {
                fileList.forEach(o -> {
                    getAllFilesAction(o, channelSftp, remoteFilePath);
                });
            }
            return SftpResponse.builder()
                    .message("Success")
                    .folderFilesSize(fileList.size())
                    .path(configuration.getLocalPath())
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error download file", ex);
        } finally {
            configuration.disconnectChannelSftp(channelSftp);
        }

        return SftpResponse.builder()
                .message("Error")
                .build();
    }

    private void getAllFilesAction(Object o, ChannelSftp channelSftp, String remoteFilePath) {
        OutputStream outputStream;
        try {
            if (o instanceof LsEntry) {
                LsEntry entry = (LsEntry) o;
                if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                    log.info(entry.getFilename());
                    File file = new File(configuration.getLocalPath() + entry.getFilename());
                    outputStream = new FileOutputStream(file);
                    channelSftp.get(remoteFilePath + entry.getFilename(), outputStream);
                    channelSftp.rm(remoteFilePath + entry.getFilename());
                    if (file.createNewFile()) {
                        log.info("file was created");
                    } else {
                        log.info("file was overwritten");
                    }
                }
            }
        } catch (SftpException | IOException ex) {
            ex.printStackTrace();
            log.error("error getAllFilesAction");
        }
    }
}
