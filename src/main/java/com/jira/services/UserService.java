package com.jira.services;

import com.jira.exceptions.UsernameAlreadyExistsException;
import com.jira.models.UserModel;
import com.jira.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel saveUser(UserModel newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        } catch(Exception e) {
            throw new UsernameAlreadyExistsException("Username" + newUser.getUsername() + " already exists!!");
        }

    }
}
