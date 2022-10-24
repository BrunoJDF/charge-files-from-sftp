package pe.bruno.com.fileattachment.config;

import com.jcraft.jsch.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Setter
@Configuration
public class SftpConfiguration {
    @Value("${config.sftp.host}")
    private String host;
    @Value("${config.sftp.port}")
    private String port;
    @Value("${config.sftp.user}")
    private String user;
    @Value("${config.sftp.password}")
    private String password;
    @Value("${config.path.local-path}")
    private String localPath;
    @Value("${config.path.remote-path}")
    private String remotePath;
    @Value("${config.job.name}")
    private String jobName;
    @Value("${config.job.param.local-path}")
    private String paramLocalPath;
    @Value("${config.job.param.remote-path}")
    private String paramRemotePath;

    public ChannelSftp createChannelSftp() {
        JSch config = new JSch();
        try {
            Session session = config.getSession(this.getUser(), this.getHost(), Integer.parseInt(this.getPort()));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(this.getPassword());
            session.connect(5000);

            Channel channel = session.openChannel("sftp");
            channel.connect(5000);
            return (ChannelSftp) channel;

        } catch (JSchException e) {
            throw new RuntimeException("Error JschException" + e.getMessage());
        }
    }

    public void disconnectChannelSftp(ChannelSftp channelSftp) {
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
