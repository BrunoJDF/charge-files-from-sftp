package pe.bruno.com.fileattachment.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.bruno.com.fileattachment.application.dto.file.FolderDto;

@RequestMapping("/sftp")
public interface FileController {

    @GetMapping("/download")
    FolderDto downloadFileFromSftp(@RequestParam String path);

}
