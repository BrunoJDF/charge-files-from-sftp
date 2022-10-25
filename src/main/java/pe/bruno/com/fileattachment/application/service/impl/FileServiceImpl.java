package pe.bruno.com.fileattachment.application.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.commons.FileStatus;
import pe.bruno.com.fileattachment.application.dto.FileDto;
import pe.bruno.com.fileattachment.application.dto.FolderDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;
import pe.bruno.com.fileattachment.persistence.model.FileEntity;
import pe.bruno.com.fileattachment.persistence.repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final SftpConfiguration configuration;
    private final FileRepository fileRepository;
    private final MapperFacade mapperFacade;

    @Override
    public FolderDto downloadFile(String remoteFilePath) {
        ChannelSftp channelSftp = configuration.createChannelSftp();
        try {
            Vector<?> fileList = channelSftp.ls(remoteFilePath);
            if (fileList.size() > 0) {
                fileList.forEach(o -> {
                    getAllFilesAction(o, channelSftp, remoteFilePath);
                });
            }
            return FolderDto.builder()
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

        return FolderDto.builder()
                .message("Error")
                .build();
    }

    @Override
    public void getAllFilesAction(Object o, ChannelSftp channelSftp, String remoteFilePath) {
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

    @Override
    public FileDto downloadFile(String remoteFilePath, String localFilePath) {
        ChannelSftp channelSftp = configuration.createChannelSftp();
        FileDto dto;
        try {
            OutputStream outputStream;
            /** File file = new File(localFilePath + "entry.getFilename()"); - Pendiente corregir **/
            File file = new File(localFilePath);
            outputStream = new FileOutputStream(file);
            /** Pendiente Corregir
             * channelSftp.get(remoteFilePath + "entry.getFilename()", outputStream);
             * channelSftp.rm(remoteFilePath + "entry.getFilename()");
             **/
            channelSftp.get(remoteFilePath, outputStream);
            channelSftp.rm(remoteFilePath);
            if (file.createNewFile()) {
                log.info("file was created");
            } else {
                log.info("file was overwritten");
            }
            dto = FileDto.builder()
                    .filename(file.getName())
                    .send(false)
                    .response(FileStatus.SAVED)
                    .path(localFilePath)
                    .build();

        } catch (Exception e) {
            log.error("error download file", e);
            dto = FileDto.builder()
                    .response(FileStatus.WITH_ERRORS)
                    .filename(null)
                    .path(null)
                    .send(false)
                    .build();
        } finally {
            configuration.disconnectChannelSftp(channelSftp);
        }
        return this.save(dto);
    }

    @Override
    public FileDto save(FileDto create) {
        var toSave = this.mapperFacade.map(create, FileEntity.class);
        return Try.of(() -> this.fileRepository.save(toSave))
                .map(saved -> this.mapperFacade.map(saved, FileDto.class))
                .get();
    }

    @Override
    public FileDto update(FileDto update, String id) {
        return null;
    }
}
