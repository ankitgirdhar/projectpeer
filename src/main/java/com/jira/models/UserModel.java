package com.jira.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class UserModel extends Audit implements UserDetails {

    @Getter
    @Setter
    @NotBlank( message = "Enter your email ID!")
    @Email( message = "Email syntax is invalid!")
    @Column(updatable = false,unique = true)
    private String username;

    @Getter
    @Setter
    @NotBlank( message = "Enter your full name!")
    private String fullname;

    @Getter
    @Setter
    @NotBlank( message = "Password field is required!")
    private String password;

    @Getter
    @Setter
    private String confirmPassword;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();


    public UserModel() {
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
