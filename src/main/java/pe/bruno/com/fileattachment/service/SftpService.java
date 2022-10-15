package pe.bruno.com.fileattachment.service;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

@Service
@AllArgsConstructor
@Slf4j
public class SftpService {
    private final SftpConfiguration configuration;

    public String downloadFile(String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        OutputStream outputStream;
        try {
            Vector fileList = channelSftp.ls(remoteFilePath);
            for (int i = 0; i < fileList.size(); i++) {
                Object obj = fileList.elementAt(i);
                if (obj instanceof LsEntry) {
                    LsEntry entry = (LsEntry) obj;
                    if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                        log.info(entry.getFilename());
                        File file = new File(configuration.getLocalPath() + entry.getFilename());
                        outputStream = new FileOutputStream(file);
                        channelSftp.get(remoteFilePath + entry.getFilename(), outputStream);
                        if (file.createNewFile()) {
                            log.info("se creo archivo");
                        } else {
                            log.info("se sobreescribio archivo");
                        }
                    }
                }
            }
            return configuration.getLocalPath();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error download file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return "Error";
    }

    private ChannelSftp createChannelSftp() {
        JSch config = new JSch();
        try {
            Session session = config.getSession(configuration.getUser(), configuration.getHost(), Integer.parseInt(configuration.getPort()));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(configuration.getPassword());
            session.connect(5000);

            Channel channel = session.openChannel("sftp");
            channel.connect(5000);
            return (ChannelSftp) channel;

        } catch (JSchException e) {
            throw new RuntimeException("Error JschException" + e.getMessage());
        }
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if (channelSftp == null)
                return;

            if (channelSftp.isConnected()) {
                channelSftp.disconnect();
            }

            if (channelSftp.getSession() != null) {
                channelSftp.getSession().disconnect();
            }
        } catch (Exception ex) {
            log.error("SFTP disconnect error", ex);
        }
    }
}
