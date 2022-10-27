package pe.bruno.com.fileattachment.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.job.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.job.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.job.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.JobService;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {


    @Override
    public JobScheduleDto create(CreateJobScheduleDto dto) {
        return null;
    }

    @Override
    public JobScheduleDto getJob(int id) {
        return null;
    }

    @Override
    public List<JobScheduleDto> getAllJob() {
        return null;
    }

    @Override
    public JobScheduleDto addJobParam(JobParamDto param, int id) {
        return null;
    }

    @Override
    public List<JobParamDto> getAllJobParamByJobScheduleId(int jobId) {
        return null;
    }

    @Override
    public JobScheduleDto getJobByJobName(String jobName) {
        return null;
    }

    @Override
    public boolean deleteJobById(int id) {
        return false;
    }
}
