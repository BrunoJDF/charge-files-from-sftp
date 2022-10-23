package pe.bruno.com.fileattachment.persistence.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
public class JobParamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column
    private String paramName;
    @Basic
    @Column
    private String paramValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_schedule_id")
    private JobScheduleEntity jobSchedule;

}
