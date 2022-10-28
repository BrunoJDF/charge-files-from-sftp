package pe.bruno.com.fileattachment.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.tieto.TokenDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.application.service.TokenService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

@Slf4j
@RequiredArgsConstructor
public class FileJobProcess implements Job {
    //private final TietoevryClient tietoevryClient;
    //private final TietoevryConfiguration tietoevryConfiguration;
    private final FileService fileService;
    private final SftpConfiguration sftpConfiguration;
    private final TokenService<TokenDto> tokenService;
    private TokenDto tokenDto;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ejecutando job");
        fileService.getFolderAction(sftpConfiguration.getRemotePath(), sftpConfiguration.getLocalPath());
        invokeLoadFile(FileDto.builder().build());

        /*PokemonDto response = pokemonClient.getPokemon("35");
        log.info("pokemon {}", response);*/
    }

    private void invokeLoadFile(FileDto savedFile) {
        //TokenDto response = tietoevryClient.getToken(tietoevryConfiguration.getTietoUser(), tietoevryConfiguration.getTietoPassword());

        TokenDto response = generateToken();
        try {
            log.info("usando token {}", response);

            throw new Exception("El token expiro");
        } catch (Exception e) {
            log.error("error ", e);
            disableToken(response);

            TokenDto tokenAgain = generateToken();
            log.info("usando tokenAgain {}", tokenAgain);
        }

    }

    private void disableToken(TokenDto response) {
        response.setActive(false);
        tokenService.update(response);
    }

    private TokenDto generateToken() {
        TokenDto response = new TokenDto();
        response.setToken("1231231231231231");
        return tokenService.save(response);
    }
}
