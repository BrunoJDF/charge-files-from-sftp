package pe.bruno.com.fileattachment.application.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.commons.ProcessStatus;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.file.FolderDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        log.info("fileList: " + fileList.size());
        return fileList.size();
    }

    @Override
    public void getAllFilesAction(Object o, String remoteFilePath, String localFilePath) throws SftpException, IOException {
        OutputStream outputStream;
        if (o instanceof LsEntry) {
            LsEntry entry = (LsEntry) o;
            log.info("Lectura de: " + entry.getFilename());
            if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && !entry.getFilename().equals(FOLDER_TEMP)) {

                File file = createFileCSV(new File(localFilePath + entry.getFilename()));
                outputStream = Files.newOutputStream(file.toPath());

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

    private File createFileCSV(File localFile) {
        log.info("Se cambia extension del archivo a copiar");
        var index = localFile.getName().lastIndexOf(".");
        var name = localFile.getName().substring(0, index);
        return new File(localFile.getParent(), name + FILE_EXTENSION);
    }

    @Override
    public FileDto save(FileDto create) {
        //TODO Implementacion para guardar archivo
        return null;
    }

    @Override
    public FileDto update(FileDto update, String id) {
        //TODO Implementacion para actualizar archivo
        return null;
    }

    @Override
    public List<File> getFolderLocalAction(String localPath) {
        File folder = new File(localPath);
        return Arrays.asList(Objects.requireNonNull(folder.listFiles()));
    }
}
