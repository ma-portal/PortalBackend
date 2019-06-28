package org.luncert.portal.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.luncert.portal.exceptions.GithubServiceError;
import org.luncert.portal.exceptions.NoCachedResourceError;
import org.luncert.portal.model.github.Repository;
import org.luncert.portal.util.IOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class GithubService {

    private static final String TOKEN_TYPE = "Bearer";

    @Data
    @JsonSerialize
    @JsonDeserialize
    private static class Resource {
        String resourceUri;
        UUID uuid;
    }

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private UserService userService;

    private static final String KEY_AUTH_DETAILS_SUFFIX = "/github/access-token";
    private static final String KEY_REQ_RES_SUFFIX = "/github/request-resource";

    public boolean authorized() {
        String account = userService.getCurrentAccount();
        if (account == null) {
            return false;
        }
        String raw = redis.opsForValue().get(account + KEY_AUTH_DETAILS_SUFFIX);
        return raw != null;
    }

    /**
     * invoke be GithubFilter
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

    /**
     * invoke by GithubController
     * 
     * @param uuid
     * @return
     * @throws GithubServiceError
     */
    public String getCachedResource(UUID uuid) throws GithubServiceError {
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
            throw new GithubServiceError("resource uuid not matched");
        }
    }

    public void updateAccessToken(String code, String state) throws Exception {
        Objects.requireNonNull(code, "parameter code must be non-null");
        Objects.requireNonNull(state, "parameter code must be non-null");

        String account = userService.getCurrentAccount();
        Objects.requireNonNull(account, "user not authorized");

        String accessToken = getAccessToken(code, state);
        redis.opsForValue().set(account + KEY_AUTH_DETAILS_SUFFIX, accessToken);
    }

    /**
     * clear Gitlab's credential cached in Redis
     * 
     * @param account this method will be invoked in SecuritySignoutSuccessHandler,
     *                so that it's not possible to get current account via
     *                SecurityContextHolder
     */
    public void logout(String account) {
        Objects.requireNonNull(account, "user not authorized");

        redis.delete(account + KEY_AUTH_DETAILS_SUFFIX);
        redis.delete(account + KEY_REQ_RES_SUFFIX);
    }

    // Github Auth API

    @Value("${github.api.token}")
    private String GITHUB_API_TOKEN;

    /**
     * 
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    public String getAccessToken(String code, String state) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet req = new HttpGet(MessageFormat.format(GITHUB_API_TOKEN, code, state));
            req.addHeader("Accept", "application/json");
            CloseableHttpResponse rep = httpClient.execute(req);
            int statusCode = rep.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                byte[] repBody = IOHelper.read(rep.getEntity().getContent());
                return JSON.parseObject(new String(repBody)).getString("access_token");
            } else {
                throw new GithubServiceError("request failed for access token, status code: " + statusCode);
            }
        }
    }

    // Github API

    private void addAuthHeader(HttpUriRequest req) {
        String account = userService.getCurrentAccount();
        Objects.requireNonNull(account, "user not authorized");
        String accessToken = redis.opsForValue().get(account + KEY_AUTH_DETAILS_SUFFIX);
        Objects.requireNonNull(accessToken, "user not authorized for Github");
        req.addHeader("Authentication", TOKEN_TYPE + " " + accessToken);
    }

    @Value("${github.api.commits}")
    private String GITHUB_API_COMMITS;

    public int getCommitsCount(String owner, String repo)
            throws ClientProtocolException, IOException, GithubServiceError {
        HttpGet req = new HttpGet(MessageFormat.format(GITHUB_API_COMMITS, owner, repo)
            + "/author=" + owner);
        addAuthHeader(req);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse rep = httpClient.execute(req);
            int statusCode = rep.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                byte[] repBody = IOHelper.read(rep.getEntity().getContent());
                JSONArray data = JSONArray.parseArray(new String(repBody));
                return data.size();
            } else {
                throw new GithubServiceError("request for commits, status code: " + statusCode);
            }
        }
    }

    @Value("${github.api.repositories}")
    private String GITHUB_API_REPOS;

    public List<Repository> getAllRepos() throws ClientProtocolException, IOException, GithubServiceError {
        HttpGet req = new HttpGet(GITHUB_API_REPOS);
        addAuthHeader(req);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse rep = httpClient.execute(req);
            int statusCode = rep.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                byte[] repBody = IOHelper.read(rep.getEntity().getContent());
                JSONArray data = JSONArray.parseArray(new String(repBody));
                return data.toJavaList(Repository.class);
            } else {
                throw new GithubServiceError("request for repositories, status code: " + statusCode);
            }
        }
    }

    @Value("#{github.api.weekly-commit-count}")
    private String GITHUB_WEEKLY_COMMIT_COUNT;

    /**
     * Returns the total commit counts in all. all is everyone combined, including the owner in the last 52 weeks.
     * @param owner
     * @param repo
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws GithubServiceError
     */
    public List<Integer> getWeeklyCommitCount(String owner, String repo) throws ClientProtocolException, IOException, GithubServiceError {
        HttpGet req = new HttpGet(MessageFormat.format(
            GITHUB_WEEKLY_COMMIT_COUNT, owner, repo
        ));
        addAuthHeader(req);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse rep = httpClient.execute(req);
            int statusCode = rep.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                byte[] repBody = IOHelper.read(rep.getEntity().getContent());
                JSONObject data = JSONObject.parseObject(new String(repBody));
                return data.getJSONArray("all").toJavaList(Integer.class);
            } else {
                throw new GithubServiceError("request for weekly commit count, status code: " + statusCode);
            }
        }
    }

}