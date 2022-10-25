package pe.bruno.com.fileattachment.persistence.model;

import lombok.Getter;
import lombok.Setter;
import pe.bruno.com.fileattachment.application.commons.FileStatus;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String filename;
    @Column
    private String path;
    @Column
    private boolean send;
    @Column
    private String token;
    @Column
    private FileStatus response;
}
