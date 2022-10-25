package pe.bruno.com.fileattachment.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderDto {
    private String path;
    private int folderFilesSize;
    private String message;
}
