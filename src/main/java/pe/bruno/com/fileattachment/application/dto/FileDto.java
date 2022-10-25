package pe.bruno.com.fileattachment.application.dto;

import lombok.Builder;
import lombok.Data;
import pe.bruno.com.fileattachment.application.commons.FileStatus;

@Data
@Builder
public class FileDto {
    private int id;
    private String filename;
    private String path;
    private boolean send;
    private String token;
    private FileStatus response;
}
