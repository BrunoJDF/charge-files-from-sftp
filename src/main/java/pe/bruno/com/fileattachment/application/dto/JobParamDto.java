package pe.bruno.com.fileattachment.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JobParamDto {
    private int id;
    private String paramName;
    private String paramValue;
    @JsonIgnore
    private int jobScheduleId;
}
