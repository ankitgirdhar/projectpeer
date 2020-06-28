package com.jira.exceptions;

import lombok.Getter;

public class ProjectNotFoundExceptionResponse {

    @Getter
    private final String projectNotFound;

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        this.projectNotFound = projectNotFound;
    }
}
