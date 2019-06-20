package org.luncert.portal.model.gitlab;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * Gitlab - User实体
 */
@Data
public class GitlabUser {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "username")
    private String username;

    @JSONField(name = "state")
    private String state;

    @JSONField(name = "avatar_url")
    private String avatarUrl;

    @JSONField(name = "webUrl")
    private String webUrl;

    @JSONField(name = "accessLevel")
    private int accessLevel;

    @JSONField(name = "expires_at")
    private String expiresAt;

}