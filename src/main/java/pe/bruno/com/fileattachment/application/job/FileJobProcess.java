package pe.bruno.com.fileattachment.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

@Slf4j
@RequiredArgsConstructor
public class FileJobProcess implements Job {
    //private final TietoevryClient tietoevryClient;
    //private final TietoevryConfiguration tietoevryConfiguration;
    private final FileService fileService;
    private final SftpConfiguration sftpConfiguration;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ejecutando job");
        fileService.getFolderAction(sftpConfiguration.getRemotePath(), sftpConfiguration.getLocalPath());
        generateToken(FileDto.builder().build());

        /*PokemonDto response = pokemonClient.getPokemon("35");
        log.info("pokemon {}", response);*/
    }

    private void generateToken(FileDto savedFile) {
        //TokenDto response = tietoevryClient.getToken(tietoevryConfiguration.getTietoUser(), tietoevryConfiguration.getTietoPassword());
        log.info("generando token ");
    }
}
