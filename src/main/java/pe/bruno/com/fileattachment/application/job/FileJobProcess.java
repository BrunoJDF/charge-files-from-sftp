package pe.bruno.com.fileattachment.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.bruno.com.fileattachment.application.dto.tieto.TokenDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.application.service.TokenService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;
import pe.bruno.com.fileattachment.config.TietoevryConfiguration;
import pe.bruno.com.fileattachment.web.exception.BadRequestException;
import pe.bruno.com.fileattachment.web.exception.NotFoundException;
import pe.bruno.com.fileattachment.web.exception.TokenExpiredException;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class FileJobProcess implements Job {
    private final FileService fileService;
    private final SftpConfiguration sftpConfiguration;
    private final TokenService<TokenDto> tokenService;
    private final TietoevryConfiguration tietoevryConfiguration;
    private final WebClient webClient;
    private final static String FILE_EXTENSION = ".csv";
    private long tokenId;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("ejecutando job");
        fileService.getFolderAction(sftpConfiguration.getRemotePath(), sftpConfiguration.getLocalPath());
        List<File> files = fileService.getFolderLocalAction(sftpConfiguration.getLocalPath());

        files.forEach(file -> {
            boolean check = notMarked(file);
            if (check) {
                log.info(file.getPath());
                invokeLoadFile(file);
                file.renameTo(createFileTXT(file));
            }
        });
    }

    private boolean notMarked(File file) {
        boolean response = true;
        try {
            int position = file.getName().lastIndexOf("_");
            String marked = file.getName().substring(0, position);
            if ("marked".equalsIgnoreCase(marked)) {
                response = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void invokeLoadFile(File sendFile) {
        TokenDto token;
        if (tokenId == 0) {
            token = generateToken();

        } else {
            token = tokenService.findByIdAndActiveTrue(tokenId)
                    .orElseThrow(() -> new NotFoundException("No existe token"));
        }

        try {
            log.info("token {}", token);
            sendFile(token.getToken(), sendFile.getPath());
        } catch (TokenExpiredException e) {
            log.error("error ", e);
            disableToken(token);

            token = generateToken();
            sendFile(token.getToken(), sendFile.getPath());
        }
        log.info("response token {}", token);
    }

    private void sendFile(String token, String path) throws TokenExpiredException {

        var response = webClient.put()
                .uri("/api/v2/configuration/feature/eir/equipments/provisioning", uriBuilder ->
                        uriBuilder.queryParam("filepath", path).build()
                )
                .cookies(cookie -> cookie.set("token", token))
                .retrieve();
        log.info("response sendFile {}", Optional.of(Objects.requireNonNull(response.toBodilessEntity().block()).getStatusCode()));
    }

    private void disableToken(TokenDto response) {
        response.setActive(false);
        tokenService.update(response);
    }

    private TokenDto generateToken() {
        TokenDto response = getToken(tietoevryConfiguration.getTietoUser(), tietoevryConfiguration.getTietoPasswd());
        TokenDto saved = tokenService.save(response);
        tokenId = saved.getId();
        return saved;
    }

    private TokenDto getToken(String tietoUser, String tietoPasswd) {
        return webClient.post()
                .uri("/token")
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromFormData("user", tietoUser)
                        .with("passwd", tietoPasswd)
                )
                .retrieve()
                .bodyToMono(TokenDto.class)
                .onErrorMap(throwable -> new BadRequestException(throwable.getMessage()))
                .block();
    }

    private File createFileTXT(File localFile) {
        var index = localFile.getName().lastIndexOf(".");
        var name = localFile.getName().substring(0, index);
        var suffix = "marked_";
        return new File(localFile.getParent(), suffix + name + FILE_EXTENSION);
    }
}
