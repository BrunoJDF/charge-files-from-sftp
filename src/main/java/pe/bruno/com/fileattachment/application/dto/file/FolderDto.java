package pe.bruno.com.fileattachment.application.dto.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderDto {
    private String path;
    private int folderFilesSize;
    private String message;
}
