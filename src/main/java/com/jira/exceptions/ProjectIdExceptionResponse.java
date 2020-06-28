package com.jira.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ProjectIdExceptionResponse {

    @Getter
    private final String projectIdentifier;

    public ProjectIdExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
