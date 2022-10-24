package pe.bruno.com.fileattachment.application.service;

import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;

import java.util.List;

public interface JobService {
    JobScheduleDto create(CreateJobScheduleDto dto);

    JobScheduleDto getJob(int id);

    List<JobScheduleDto> getAllJob();

    JobScheduleDto addJobParam(JobParamDto param, int id);

    List<JobParamDto> getAllJobParamByJobScheduleId(int jobId);

    JobScheduleDto getJobByJobName(String jobName);

    boolean deleteJobById(int id);
}
