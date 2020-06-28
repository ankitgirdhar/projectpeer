package com.jira.services;

import com.jira.models.UserModel;
import com.jira.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(s);

        if(user == null)
            throw new UsernameNotFoundException("User is not found!");
        return user;
    }

    @Transactional
    public UserModel loadUserById(Long id) {
        UserModel user = userRepository.getById(id);
        if(user == null)
            throw new UsernameNotFoundException("User not found!!");
        return user;
    }
}
