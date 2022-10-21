package pe.bruno.com.fileattachment.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobParamDto {
    private int id;
    private String paramName;
    private String paramValue;
    private int jobId;
}
