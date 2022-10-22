package pe.bruno.com.fileattachment.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class JobParamEntity {

    @Id
    private int paramId;
    @Column
    private String paramName;
    @Column
    private String paramValue;

    @ManyToOne
    @JoinColumn(name = "job_schedule_id", insertable = false, updatable = false, nullable = false)
    private JobScheduleEntity jobSchedule;
}
