package pe.bruno.com.fileattachment.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileResponseDto {
    private String path;
    private int folderFilesSize;
    private String message;
}
