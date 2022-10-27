package pe.bruno.com.fileattachment.application.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.commons.FileStatus;
import pe.bruno.com.fileattachment.application.commons.ProcessStatus;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.file.FolderDto;
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
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String FILE_EXTENSION = ".csv";
    private static final String FOLDER_TEMP = "temp";
    private static final String FILE_WAS_CREATED = "File was created";
    private static final String FILE_WAS_OVERWRITTEN = "File was overwritten";
    private final SftpConfiguration configuration;
    private final FileRepository fileRepository;
    private final MapperFacade mapperFacade;
    private ChannelSftp channelSftp;

    @Override
    public FolderDto downloadAction(String remoteFilePath) {
        var fileSize = getFolderAction(remoteFilePath, configuration.getLocalPath());
        return FolderDto.builder()
                .message(ProcessStatus.SUCCESS.name())
                .folderFilesSize(fileSize)
                .path(configuration.getLocalPath())
                .build();
    }

    @Override
    public int getFolderAction(String remoteFilePath, String localFilePath) {
        channelSftp = configuration.createChannelSftp();
        Vector<?> fileList = new Vector<>();
        try {
            fileList = channelSftp.ls(remoteFilePath);
            if (fileList.size() > 0) {
                for (Object o :
                        fileList) {
                    getAllFilesAction(o, remoteFilePath, localFilePath);
                }
            }
        } catch (SftpException | IOException ex) {
            log.error("error getFolderAction", ex);
        } finally {
            configuration.disconnectChannelSftp(channelSftp);
        }
        return fileList.size();
    }

    @Override
    public void getAllFilesAction(Object o, String remoteFilePath, String localFilePath) throws SftpException, IOException {
        OutputStream outputStream;
        if (o instanceof LsEntry) {
            LsEntry entry = (LsEntry) o;
            if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && !entry.getFilename().equals(FOLDER_TEMP)) {

                File file = createFileCSV(new File(localFilePath + entry.getFilename()));
                outputStream = new FileOutputStream(file);

                channelSftp.get(remoteFilePath + entry.getFilename(), outputStream);
                try {
                    channelSftp.rename(remoteFilePath + entry.getFilename(), configuration.getTempPath() + entry.getFilename());
                } catch (SftpException e) {
                    channelSftp.mkdir(configuration.getTempPath());
                    channelSftp.rename(remoteFilePath + entry.getFilename(), configuration.getTempPath() + entry.getFilename());
                }
                var isCreated = file.createNewFile();
                if (isCreated) {
                    log.info(FILE_WAS_CREATED);
                } else {
                    log.info(FILE_WAS_OVERWRITTEN);
                }
            }
        }
    }

    @Override
    public FileDto downloadAction(String remoteFilePath, String localFilePath) {
        ChannelSftp channelSftp = configuration.createChannelSftp();
        FileDto dto;
        try {
            OutputStream outputStream;
            /** File file = new File(localFilePath + "entry.getFilename()"); - Pendiente corregir **/
            File file = createFileCSV(new File(localFilePath));
            outputStream = new FileOutputStream(file);
            /** Pendiente Corregir
             * channelSftp.get(remoteFilePath + "entry.getFilename()", outputStream);
             * channelSftp.rm(remoteFilePath + "entry.getFilename()");
             **/
            channelSftp.get(remoteFilePath, outputStream);
            //channelSftp.rm(remoteFilePath);
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

    private File createFileCSV(File localFile) {
        var index = localFile.getName().lastIndexOf(".");
        var name = localFile.getName().substring(0, index);
        return new File(localFile.getParent(), name + FILE_EXTENSION);
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
