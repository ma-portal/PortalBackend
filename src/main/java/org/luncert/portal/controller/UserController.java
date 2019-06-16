package org.luncert.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.User;
import org.luncert.portal.service.UserService;
import org.luncert.portal.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/avatar/{account}")
    public JSONObject getAvatar(@PathVariable("account") String account) {
        JSONObject json = new JSONObject();
        json.put("avatar", userService.getAvatar(account));
        return json;
    }

    @GetMapping("/profile")
    public User getProfile() {
        return userService.queryUser(getCurrentAccount());
    }

    @PutMapping
    public ResponseEntity<JSONObject> updateProfile(@RequestBody String data) {
        JSONObject ret = new JSONObject();
        try {
            userService.updateProfile(getCurrentAccount(), data);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            ret.put("errmsg", NormalUtil.throwableToString(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/role/{roleName}")
    public void addRole() {

    }

    @DeleteMapping("/role/{roleName}")
    public void removeRole() {

    }

    /**
     * NOTE: not hasRole but hasAuthority
     * @param account
     * @return
     */
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Object> addUser(@RequestParam String account) {
        User user = userService.queryUser(account);
        JSONObject json = new JSONObject();
        if (user != null) {
            json.put("errmsg", "account existed");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        } else {
            String initPwd = new ObjectId().toHexString();
            userService.addUser(account, initPwd);

            json.put("initialPassword", initPwd);
            return new ResponseEntity<>(json, HttpStatus.CREATED);
        }

    }

    private String getCurrentAccount() {
        return org.springframework.security.core.userdetails.User.class.cast
        (SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

}