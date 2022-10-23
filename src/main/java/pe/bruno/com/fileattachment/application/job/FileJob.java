package pe.bruno.com.fileattachment.application.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.service.impl.FileServiceImpl;

@Slf4j
@AllArgsConstructor
public class FileJob implements Job {

    private final FileServiceImpl service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //log.info("Execute job");
    }
}
