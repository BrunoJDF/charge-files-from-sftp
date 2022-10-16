package pe.bruno.com.fileattachment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SftpResponse {
    private String path;
    private int folderFilesSize;
    private String message;
}
