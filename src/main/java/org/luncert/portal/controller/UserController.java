package org.luncert.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.User;
import org.luncert.portal.model.mongo.User.Role;
import org.luncert.portal.repos.mongo.UserRepos;
import org.luncert.portal.service.StaticResourceService;
import org.luncert.portal.service.UserService;
import org.luncert.portal.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private StaticResourceService resourceService;

    /**
     * 不需要授权
     * 
     * @param account
     * @return
     */
    @GetMapping("/avatar/{account}")
    public ResponseEntity<JSONObject> getAvatar(@PathVariable("account") String account) {
        JSONObject json = new JSONObject();
        String avatarUri = userService.getAvatar(account);
        if (avatarUri == null) {
            json.put("errmsg", "invalid account");
            return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
        } else {
            json.put("avatar", avatarUri);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }

    /**
     * 图片会被上传到另一个微服务，然后这边只需要更新avatar_url
     * 
     * @throws Exception
     */
    @PostMapping("/avatar")
    public ResponseEntity<JSONObject> updateAvatar(HttpServletRequest req, @RequestParam("avatar") MultipartFile file) {
        JSONObject ret = new JSONObject();
        try {
            String resId = resourceService.save(file);
            // TODO:
            String resUrl = MessageFormat.format("http://{0}:{1}/static-resource/{2}",
                                req.getServerName(), req.getServerPort(), resId);
            User user = userService.getCurrentUser(true);
            user.setAvatar(resUrl);
            userRepos.save(user);

            ret.put("avatar", resUrl);
            return new ResponseEntity<>(ret, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            ret.put("errmsg", NormalUtil.throwableToString(e));
            return new ResponseEntity<>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile")
    public User getProfile() {
        return userService.getCurrentUser(false);
    }

    @PutMapping("/profile")
    public ResponseEntity<JSONObject> updateProfile(@RequestBody String data) {
        try {
            userService.updateProfile(data);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            JSONObject ret = new JSONObject();
            ret.put("errmsg", NormalUtil.throwableToString(e));
            return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/role/{roleName}")
    public ResponseEntity<JSONObject> addRole(@RequestParam("targetAccount") String tarAccount,
        @PathVariable("roleName") Role role) {
        return updateRoles(tarAccount, role, true);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/role/{roleName}")
    public ResponseEntity<JSONObject> removeRole(@RequestParam("targetAccount") String tarAccount, @PathVariable("roleName") Role role) {
        return updateRoles(tarAccount, role, false);
    }

    private ResponseEntity<JSONObject> updateRoles(String account, Role role, boolean addRole) {
        JSONObject errMsg = new JSONObject();
        User user = userService.queryUser(account, true);
        if (user == null) {
            errMsg.put("errmsg", "invalid account");
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        } else {
            Set<Role> roles = user.getRoles();
            if (addRole) {
                if (roles.contains(role)) {
                    errMsg.put("errmsg", "role exists");
                } else {
                    roles.add(role);
                    userRepos.save(user);
                }
            } else {
                if (!roles.contains(role)) {
                    errMsg.put("errmsg", "role not exists");
                } else {
                    roles.remove(role);
                    userRepos.save(user);
                }
            }
            return new ResponseEntity<>(errMsg,
                errMsg.containsKey("errmsg") ? HttpStatus.BAD_REQUEST : HttpStatus.ACCEPTED);
        }
    }

    /**
     * NOTE: not hasRole but hasAuthority
     * @param account
     * @return
     */
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<JSONObject> addUser(@RequestParam String account) {
        JSONObject json = new JSONObject();
        User user = userService.queryUser(account, false);
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

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping
    public ResponseEntity<JSONObject> deleteUser(@RequestParam String account) {
        User user = userService.queryUser(account, false);
        if (user == null) {
            JSONObject errmsg = new JSONObject();
            errmsg.put("errmsg", "invalid account");
            return new ResponseEntity<>(errmsg, HttpStatus.BAD_REQUEST);
        } else {
            userRepos.deleteByAccount(account);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/project")
    public String getProject() {
        return "ok";
    }

}