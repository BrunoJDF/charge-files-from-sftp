package pe.bruno.com.fileattachment.application.job;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.dto.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.application.service.impl.FileServiceImpl;
import pe.bruno.com.fileattachment.config.SftpConfiguration;
import pe.bruno.com.fileattachment.web.exception.NotFoundException;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FileJobProcess implements Job {
    private static final String NOT_FOUND_JOB_PARAM = "Not found job param";

    private final FileServiceImpl fileService;
    private final JobService jobService;
    private final MapperFacade mapperFacade;
    private final SftpConfiguration configuration;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Execute job");
        var job = Try.of(() -> jobService.getJobByJobName(configuration.getJobName()));
        List<JobParamDto> params = job.map(JobScheduleDto::getParams).get();
        var localPath = params.stream()
                .filter(jobParamDto -> jobParamDto.getParamName().equals(configuration.getParamLocalPath()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_JOB_PARAM));
        var remotePath = params.stream()
                .filter(jobParamDto -> jobParamDto.getParamName().equals(configuration.getParamRemotePath()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_JOB_PARAM));
        fileService.downloadFile(remotePath.getParamValue(), localPath.getParamValue());
    }
}
