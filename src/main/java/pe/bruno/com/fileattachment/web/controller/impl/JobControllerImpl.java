package pe.bruno.com.fileattachment.web.controller.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.web.controller.JobController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class JobControllerImpl implements JobController {

    private final JobService service;

    @Override
    public ResponseEntity<JobScheduleDto> getJob(String id) {
        return ResponseEntity.ok(service.getJob(Integer.parseInt(id)));
    }

    @Override
    public List<JobScheduleDto> getAllJob() {
        return service.getAllJob();
    }

    @Override
    public ResponseEntity<JobScheduleDto> create(CreateJobScheduleDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @Override
    public ResponseEntity<Boolean> delete(String id) {
        return ResponseEntity.ok(service.deleteJobById(Integer.parseInt(id)));
    }

    @Override
    public ResponseEntity<JobScheduleDto> addJobParam(JobParamDto param, String id) {
        return ResponseEntity.ok(service.addJobParam(param, Integer.parseInt(id)));
    }

    @Override
    public ResponseEntity<JobScheduleDto> getJobByJobName(String jobName) {
        return ResponseEntity.ok(service.getJobByJobName(jobName));
    }

    @Override
    public List<JobParamDto> getJobParamByJobScheduleId(String jobId) {
        return service.getAllJobParamByJobScheduleId(Integer.parseInt(jobId));
    }
}
