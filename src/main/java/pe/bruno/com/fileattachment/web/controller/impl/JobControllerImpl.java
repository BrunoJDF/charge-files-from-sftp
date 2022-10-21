package pe.bruno.com.fileattachment.web.controller.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.bruno.com.fileattachment.application.dto.JobDto;
import pe.bruno.com.fileattachment.application.service.JobService;
import pe.bruno.com.fileattachment.web.controller.JobController;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class JobControllerImpl implements JobController {

    private final JobService service;

    @Override
    public ResponseEntity<JobDto> getJob(String id) {
        return null;
    }

    @Override
    public List<JobDto> getAllJob() {
        return null;
    }

    @Override
    public ResponseEntity<JobDto> create(JobDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @Override
    public ResponseEntity<Boolean> delete(String id) {
        return null;
    }
}
