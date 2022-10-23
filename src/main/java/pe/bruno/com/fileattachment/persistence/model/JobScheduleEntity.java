package pe.bruno.com.fileattachment.persistence.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class JobScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column
    private String jobName;

    @OneToMany(mappedBy = "jobSchedule", orphanRemoval = true)
    @ToString.Exclude
    private List<JobParamEntity> params = new ArrayList<>();

}
