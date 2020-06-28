package com.jira.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "project")
public class Project extends Audit{

    @NotBlank(message = "Project name is required")
    @Getter
    @Setter
    private String projectName;


    @Getter
    @Setter
    private String projectLeader;


    @NotBlank(message = "Project identifier is required")

    @Size(min=4, max=5, message = "Please use 4 to 5 characters")
    @Column(updatable = false,unique = true)
    @Getter
    @Setter
    private String projectIdentifier;


    @NotBlank(message = "Project description is required")
    @Getter
    @Setter
    private String description;


    @JsonFormat(pattern = "yyyy-mm-dd")
    @Getter
    @Setter
    private Date start_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Getter
    @Setter
    private Date end_date;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    @Getter
    @Setter
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JsonIgnore
    private UserModel user;


    public Project() {
    }
}
