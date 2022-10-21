package pe.bruno.com.fileattachment.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobDto {
    private int id;
    private String jobName;
    private List<JobParamDto> params;
}
