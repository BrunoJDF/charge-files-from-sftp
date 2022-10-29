package pe.bruno.com.fileattachment.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.client.tieto.TietoevryClient;
import pe.bruno.com.fileattachment.application.client.tieto.TietoevryConfiguration;
import pe.bruno.com.fileattachment.application.dto.tieto.TokenDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.application.service.TokenService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileJobProcess implements Job {
    private final TietoevryClient tietoevryClient;
    private final TietoevryConfiguration tietoevryConfiguration;
    private final FileService fileService;
    private final SftpConfiguration sftpConfiguration;
    private final TokenService<TokenDto> tokenService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ejecutando job");
        fileService.getFolderAction(sftpConfiguration.getRemotePath(), sftpConfiguration.getLocalPath());
        List<File> files = fileService.getFolderLocalAction(sftpConfiguration.getLocalPath());
        files.forEach(this::invokeLoadFile);

    }

    private void invokeLoadFile(File sendFile) {

        TokenDto response = generateToken();
        try {
            tietoevryClient.sendFile(response.getToken(), sendFile.getPath());
        } catch (Exception e) {
            log.error("error ", e);
            disableToken(response);

            response = generateToken();
            tietoevryClient.sendFile(response.getToken(), sendFile.getPath());
        }
        log.info("response token {}", response);
    }

    private void disableToken(TokenDto response) {
        response.setActive(false);
        tokenService.update(response);
    }

    private TokenDto generateToken() {
        TokenDto response = tietoevryClient.getToken(tietoevryConfiguration.getTietoUser(), tietoevryConfiguration.getTietoPassword());
        return tokenService.save(response);
    }
}
