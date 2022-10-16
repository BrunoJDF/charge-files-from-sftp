package pe.bruno.com.fileattachment.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.bruno.com.fileattachment.model.SftpResponse;
import pe.bruno.com.fileattachment.service.SftpService;

@RestController
@RequestMapping("sftp")
@AllArgsConstructor
public class SftpController {
    private final SftpService service;

    @GetMapping("download")
    public SftpResponse getFile(@RequestParam String path) {
        return service.downloadFile(path);
    }
}
