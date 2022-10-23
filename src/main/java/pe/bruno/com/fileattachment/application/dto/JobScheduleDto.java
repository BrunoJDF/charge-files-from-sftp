package pe.bruno.com.fileattachment.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobScheduleDto {
    private int id;
    private String jobName;
    private List<JobParamDto> params;

    public void addParams(JobParamDto param) {
        if(this.params != null && this.params.size() == 0) {
            this.params.add(param);
        }
    }
}
