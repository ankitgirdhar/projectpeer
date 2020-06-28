package com.jira.exceptions;

import lombok.Getter;
import lombok.Setter;

public class InvalidLoginResponse {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public InvalidLoginResponse() {
        this.setUsername("Invalid Username");
        this.setPassword("Invalid Password");
    }
}
