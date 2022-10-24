package pe.bruno.com.fileattachment.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.bruno.com.fileattachment.application.dto.CreateJobScheduleDto;
import pe.bruno.com.fileattachment.application.dto.JobParamDto;
import pe.bruno.com.fileattachment.application.dto.JobScheduleDto;

import java.util.List;

@RequestMapping("/job")
public interface JobController {

    @GetMapping
    List<JobScheduleDto> getAllJob();

    @PostMapping
    ResponseEntity<JobScheduleDto> create(@RequestBody CreateJobScheduleDto request);

    @GetMapping("/{id}")
    ResponseEntity<JobScheduleDto> getJob(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@PathVariable String id);

    @PostMapping("/{id}/params")
    ResponseEntity<JobScheduleDto> addJobParam(@RequestBody JobParamDto param, @PathVariable String id);

    @GetMapping("/find/name")
    ResponseEntity<JobScheduleDto> getJobByJobName(@RequestParam("jobName") String jobName);

    @GetMapping("/{id}/find/job_params")
    List<JobParamDto> getJobParamByJobScheduleId(@PathVariable("id") String jobId);
}
