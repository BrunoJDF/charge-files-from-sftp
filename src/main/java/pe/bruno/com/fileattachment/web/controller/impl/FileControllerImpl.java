package pe.bruno.com.fileattachment.web.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pe.bruno.com.fileattachment.application.dto.FileResponseDto;
import pe.bruno.com.fileattachment.application.service.FileService;
import pe.bruno.com.fileattachment.web.controller.FileController;

@RestController
@AllArgsConstructor
public class FileControllerImpl implements FileController {
    private final FileService service;

    @Override
    public FileResponseDto downloadFileFromSftp(String path) {
        return service.downloadFile(path);
    }


}
