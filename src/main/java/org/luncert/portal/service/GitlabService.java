package org.luncert.portal.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.luncert.portal.exceptions.GitlabServiceError;
import org.luncert.portal.exceptions.NoCachedResourceError;
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

    @Data
    @JsonSerialize
    @JsonDeserialize
    private static class Resource {
        String resourceUri;
        UUID uuid;
    }

    @Value("${gitlab.auth-uri-token}")
    private String GITLAB_AUTH_TOKEN;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private UserService userService;

    private static final String KEY_AUTH_DETAILS_SUFFIX = "/gitlab/auth-details";
    private static final String KEY_REQ_RES_SUFFIX = "/gitlab/request-resource";

    public boolean authorized() {
        String account = userService.getCurrentAccount();
        if (account == null) {
            return false;
        }
        String raw = redis.opsForValue().get(account + KEY_AUTH_DETAILS_SUFFIX);
        return raw != null;
    }

    /**
     * 
     * @param uri
     * @return state
     */
    public String cacheRequestResource(String uri) {
        Objects.requireNonNull(uri, "parameter uuid must be non-null");

        String account = userService.getCurrentAccount();
        Objects.requireNonNull(account, "user not authorized");

        Resource res = new Resource();
        res.setResourceUri(uri);
        res.setUuid(UUID.randomUUID());
        redis.opsForValue().set(account + KEY_REQ_RES_SUFFIX, JSONObject.toJSONString(res));

        return res.getUuid().toString();
    }

    public String getCachedResource(UUID uuid) throws GitlabServiceError {
        Objects.requireNonNull(uuid, "parameter uuid must be non-null");

        String account = userService.getCurrentAccount();
        Objects.requireNonNull(account, "user not authorized");

        String raw = redis.opsForValue().get(account + KEY_REQ_RES_SUFFIX);
        if (raw == null) {
            throw new NoCachedResourceError(account);
        }
        Resource res = JSONObject.parseObject(raw, Resource.class);
        if (res.getUuid().equals(uuid)) {
            return res.getResourceUri();
        } else {
            throw new GitlabServiceError("resource uuid not matched");
        }
    }

    public void updateAuthDetails(String code) throws GitlabServiceError, UnsupportedOperationException, IOException {
        Objects.requireNonNull(code, "parameter code must be non-null");
        
        String account = userService.getCurrentAccount();
        Objects.requireNonNull(account, "user not authorized");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost req = new HttpPost(MessageFormat.format(GITLAB_AUTH_TOKEN, code));
            CloseableHttpResponse rep = httpClient.execute(req);
            int statusCode = rep.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                byte[] repBody = IOHelper.read(rep.getEntity().getContent());

                try {
                    JSONObject.parseObject(repBody, AuthDetails.class);
                } catch (Exception e) {
                    throw new GitlabServiceError(e);
                }

                redis.opsForValue().set(account + KEY_AUTH_DETAILS_SUFFIX, new String(repBody));
            } else {
                throw new GitlabServiceError("request for access token failed, status code: " + statusCode);
            }
        }
    }

    /**
     * /api/v4/projects: 获取所有对当前授权用户可见的project，如果没有经过授权，该接口仅返回所有public的project
     */
    public void allProjects() {
    }

}