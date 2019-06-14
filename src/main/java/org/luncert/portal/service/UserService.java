package org.luncert.portal.service;

import java.util.Collections;

import org.luncert.portal.model.mongo.User;
import org.luncert.portal.model.mongo.User.Role;
import org.luncert.portal.repos.mongo.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepos userRepos;

    public User queryAccount(String account) {
        return userRepos.findByAccount(account);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(String account, String password) {
        User user = User.builder()
            .account(account)
            .password(passwordEncoder.encode(password))
            .roles(Collections.singletonList(Role.Normal))
            .build();
        userRepos.save(user);
    }

    public String getAvatar(String account) {
        return userRepos.findAvatarByAccount(account);
    }

}