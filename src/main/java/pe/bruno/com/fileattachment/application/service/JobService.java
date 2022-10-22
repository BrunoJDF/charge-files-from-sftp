package pe.bruno.com.fileattachment.application.service;

import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;

import java.util.List;

public interface JobService {
    JobScheduleDto create(CreateJobScheduleDto dto);

    JobScheduleDto getJob(int id);

    List<JobScheduleDto> getAllJob();
}
