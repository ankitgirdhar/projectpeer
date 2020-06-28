package com.jira.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    @Getter
    @Setter
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Getter
    @Setter
    private Date updated_At;

    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }
}
