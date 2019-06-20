package org.luncert.portal.model.gitlab;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class Commit {
    
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "short_id")
    private String shortId;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "author_name")
    private String authorName;

    @JSONField(name = "author_email")
    private String authorEmail;

    @JSONField(name = "authored_date")
    private String authoredDate;

    @JSONField(name = "committer_name")
    private String committerName;

    @JSONField(name = "committer_email")
    private String committerEmail;

    @JSONField(name = "committed_date")
    private String committedDate;

    @JSONField(name = "created_at")
    private String createdAt;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "parent_ids")
    private List<String> parentIds;

}