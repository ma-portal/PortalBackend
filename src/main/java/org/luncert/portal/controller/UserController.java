package org.luncert.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import org.luncert.portal.model.mongo.User;
import org.luncert.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/avatar/{account}")
    public String getAvatar(@PathVariable("account") String account) {
        JSONObject json = new JSONObject();
        json.put("avatar", userService.getAvatar(account));
        return json.toJSONString();
    }

    @GetMapping("/profile")
    public User getProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userService.queryAccount(userDetails.getUsername());
    }

    @PreAuthorize("hasRole(Admin)")
    @PostMapping
    public void addUser(@RequestParam String account, @RequestParam String password) {
        userService.addUser(account, password);
    }
    

}