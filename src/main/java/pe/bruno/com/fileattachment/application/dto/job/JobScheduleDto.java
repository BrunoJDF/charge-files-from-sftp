package pe.bruno.com.fileattachment.application.dto.job;

import lombok.Data;

import java.util.List;

@Data
public class JobScheduleDto {
    private int id;
    private String jobName;
    private List<JobParamDto> params;

}
