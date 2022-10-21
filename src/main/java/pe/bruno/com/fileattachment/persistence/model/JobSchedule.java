package pe.bruno.com.fileattachment.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class JobSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String jobName;

    @OneToMany(mappedBy = "jobSchedule")
    private List<JobParam> params;
}
