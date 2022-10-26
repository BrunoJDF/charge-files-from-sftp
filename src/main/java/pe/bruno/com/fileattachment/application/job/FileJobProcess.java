package pe.bruno.com.fileattachment.application.job;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.client.TietoevryClient;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.job.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.job.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.config.SftpConfiguration;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FileJobProcess implements Job {
    private static final String NOT_FOUND_JOB_PARAM = "Not found job param";

    private final FileService fileService;
    private final JobService jobService;
    private final TietoevryClient tietoevryClient;
    private final MapperFacade mapperFacade;
    private final SftpConfiguration configuration;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var job = Try.of(() -> jobService.getJobByJobName(configuration.getJobName()));
        List<JobParamDto> params = job.map(JobScheduleDto::getParams).get();

        //var savedFile = fileService.downloadFile(remotePath.getParamValue(), localPath.getParamValue());

        //generateToken(savedFile);

        var response = tietoevryClient.getPokemon("35");
        log.info("pokemon {}", response);
    }

    private void generateToken(FileDto savedFile) {
        log.info("generando token");
    }
}
