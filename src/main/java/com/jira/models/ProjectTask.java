package com.jira.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "project_task")
public class ProjectTask extends Audit{

    @Column(updatable = false, unique = true)
    @Getter
    @Setter
    private String projectSequence;

    @NotBlank(message = "please include the project summary")
    @Getter
    @Setter
    private String summary;

    @Getter
    @Setter
    private String acceptanceCriteria;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private Integer priority;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Getter
    @Setter
    private Date dueDate;

    @Column(updatable = false)
    @Getter
    @Setter
    private String projectIdentifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonBackReference
    @Getter
    @Setter
    private Backlog backlog;

    public ProjectTask() {
    }

    @Override
    public String toString() {
        return "ProjectTask{" +
                "projectSequence='" + projectSequence + '\'' +
                ", summary='" + summary + '\'' +
                ", acceptanceCriteria='" + acceptanceCriteria + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", projectIdentifier='" + projectIdentifier + '\'' +
                ", backlog=" + backlog +
                '}';
    }
}
