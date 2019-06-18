package org.luncert.portal.service;

import java.io.IOException;
import java.text.MessageFormat;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.luncert.portal.model.mongo.User;
import org.luncert.portal.util.IOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class GitlabService {

    @Data
    @JsonSerialize
    @JsonDeserialize
    private static class AuthDetails {
        String accessToken;
        String tokenType;
        String refreshToken;
        String scope;
        long createdAt;
    }

    @Value("${gitlab.server.uri}")
    private String GITLAB_SERVER_URI;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private UserService userService;

    private static final String REDIS_KEY_SUFFIX = "/gitlab/access-token";

    private AuthDetails getAuthDetails() throws Exception {
        String account = userService.getCurrentAccount();
        String raw = redis.opsForValue().get(account + REDIS_KEY_SUFFIX);
        if (raw != null) {
            AuthDetails authDetails = JSONObject.parseObject(raw, AuthDetails.class);
            // TODO: what is refresh_token create for?
            return authDetails;
        } else {
            // TODO: how to disable password modification of Gitlab
            User user = userService.queryUser(account);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost req = new HttpPost(MessageFormat.format(
                    "{}/oauth/token?grant_type=password&username={}&password={}",
                        GITLAB_SERVER_URI, account, user.getPassword()));
                CloseableHttpResponse rep = httpClient.execute(req);
                int statusCode = rep.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    byte[] repBody = IOHelper.read(rep.getEntity().getContent());
                    AuthDetails authDetails = JSONObject.parseObject(repBody, AuthDetails.class);
                    redis.opsForValue().set(account + REDIS_KEY_SUFFIX, new String(repBody));
                    return authDetails;
                } else {
                    throw new Exception("request for access token failed, status code: " + statusCode);
                }
            }
        }
    }

    public void fetchAccessToken() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            
        } catch (IOException e) {

        }
    }

    /**
     * /api/v4/projects: 获取所有对当前授权用户可见的project，如果没有经过授权，该接口仅返回所有public的project
     */
    public void allProjects() {
    }

}