package pe.bruno.com.fileattachment.config;

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

}
