package com.jira.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "backlog")
public class Backlog extends Audit{

    @Getter
    @Setter
    private Integer PTSequence = 0;

    @Getter
    @Setter
    private String projectIdentifier;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @Getter
    @Setter
    private Project project;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    @JsonManagedReference
    @Getter
    @Setter
    private List<ProjectTask> projectTasks = new ArrayList<>();

    public Backlog() {
    }
}
