package org.luncert.portal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoException;

import org.bson.Document;
import org.luncert.portal.model.mongo.User;
import org.luncert.portal.model.mongo.User.Role;
import org.luncert.portal.repos.mongo.UserRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private MongoTemplate mongoTemplate;

    public User queryUser(String account) {
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
        Query query = new BasicQuery("{account:'" + account + "'}", "{'avatar': 1}");
        DocAvatarHandler handler = new DocAvatarHandler();
        mongoTemplate.executeQuery(query, "User", handler);
        if (handler.avatarList.size() > 1) {
            logger.warn("more than one result of avatar querying, account: " + account);
        }
        return handler.avatarList.size() > 0 ?
            handler.avatarList.get(0) : "";
    }

    public void updateAccess(String account) {
        mongoTemplate.updateFirst(
            new BasicQuery("{account: '" + account + "'}"),
            new BasicUpdate("{$set: {lastAccess: " + System.currentTimeMillis() + "}}"),
            User.class);
    }

    public void updateProfile(String account, String data) throws Exception {
        JSONObject updates = JSONObject.parseObject(data);
        User user = queryUser(account);
        for (String key : updates.keySet()) {
            switch(key) {
            case "password":
                user.setPassword(passwordEncoder.encode(
                    updates.getString(key)
                ));
                break;
            case "classOf":
                user.setClassOf(updates.getInteger(key));
                break;
            case "joinTime":
                user.setJoinTime(updates.getLong(key));
                break;
            case "lastAccess":
                user.setLastAccess(updates.getLong(key));
                break;
            case "tags":
                JSONArray tags = updates.getJSONArray(key);
                user.setTags(tags.toJavaList(String.class));
                break;
            case "realName":
                user.setRealName(updates.getString(key));
                break;
            case "description":
                user.setDescription(updates.getString(key));
                break;
            case "email":
                user.setEmail(updates.getString(key));
                break;
            case "qq":
                user.setQq(updates.getString(key));
                break;
            case "phone":
                user.setPhone(updates.getString(key));
                break;
            case "avatar":
                user.setAvatar(updates.getString(key));
                break;
            default:
                throw new Exception("update forbidden to field: " + key);
            }
        }
    }

    private class DocAvatarHandler implements DocumentCallbackHandler {

        List<String> avatarList = new ArrayList<>();

        @Override
        public void processDocument(Document document) throws MongoException, DataAccessException {
            avatarList.add(document.getString("avatar"));
        }

    }

}