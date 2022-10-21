package pe.bruno.com.fileattachment.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.bruno.com.fileattachment.application.dto.JobDto;

import java.util.List;

@RequestMapping("/job")
public interface JobController {

    @GetMapping("/{id}")
    ResponseEntity<JobDto> getJob(@PathVariable String id);

    @GetMapping
    List<JobDto> getAllJob();

    @PostMapping
    ResponseEntity<JobDto> create(@RequestBody JobDto request);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@PathVariable String id);
}
