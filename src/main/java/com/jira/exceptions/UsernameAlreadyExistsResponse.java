package com.jira.exceptions;

import lombok.Getter;
import lombok.Setter;

public class UsernameAlreadyExistsResponse {
    @Getter
    @Setter
    String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }
}
