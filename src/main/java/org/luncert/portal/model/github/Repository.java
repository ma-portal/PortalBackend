package org.luncert.portal.model.github;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class Repository {

    @JSONField(name = "id")
    private long id;

    @JSONField(name = "node_id")
    private String nodeId;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "full_name")
    private String fullName;

    @JSONField(name = "private")
    private boolean bePrivate;

    @JSONField(name = "owner")
    private GithubUser owner;

    @JSONField(name = "html_url")
    private String htmlUrl;
    
    @JSONField(name = "description")
    private String description;
    
    @JSONField(name = "fork")
    private boolean fork;
    
    @JSONField(name = "url")
    private String url;
    
    @JSONField(name = "forks_url")
    private String forksUrl;
    
    @JSONField(name = "keys_url")
    private String keysUrl;
    
    @JSONField(name = "collaborators_url")
    private String collaboratorsUrl;

    @JSONField(name = "teams_url")
    private String teamsUrl;

    @JSONField(name = "hooks_url")
    private String hooksUrl;

    @JSONField(name = "issue_events_url")
    private String issueEventsUrl;

    @JSONField(name = "events_url")
    private String eventsUrl;
    
    @JSONField(name = "assignees_url")
    private String assigneesUrl;

    @JSONField(name = "branches_url")
    private String branchesUrl;

    @JSONField(name = "tags_url")
    private String tagsUrl;

    @JSONField(name = "blobs_url")
    private String blobsUrl;

    @JSONField(name = "git_tags_url")
    private String gitTagsUrl;

    @JSONField(name = "git_refs_url")
    private String gitRefsUrl;

    @JSONField(name = "trees_url")
    private String treesUrl;

    @JSONField(name = "statuses_url")
    private String statusesUrl;

    @JSONField(name = "languages_url")
    private String languagesUrl;

    @JSONField(name = "stargazers_url")
    private String stargazersUrl;

    @JSONField(name = "contributors_url")
    private String contributorsUrl;

    @JSONField(name = "subscribers_url")
    private String subscribersUrl;

    @JSONField(name = "subscription_url")
    private String subscriptionUrl;

    @JSONField(name = "commits_url")
    private String commitsUrl;

    @JSONField(name = "comments_url")
    private String commentsUrl;

    @JSONField(name = "issue_comment_url")
    private String issueCommentUrl;

    @JSONField(name = "content_url")
    private String contentUrl;

    @JSONField(name = "compare_url")
    private String compareUrl;

    @JSONField(name = "merges_url")
    private String mergesUrl;

    @JSONField(name = "archive_url")
    private String archiveUrl;

    @JSONField(name = "downloads_url")
    private String downloadsUrl;

    @JSONField(name = "pulls_url")
    private String pullsUrl;

    @JSONField(name = "milestones_url")
    private String milestonesUrl;

    @JSONField(name = "notifications_url")
    private String notificationsUrl;

    @JSONField(name = "labels_url")
    private String labelsUrl;

    @JSONField(name = "releases_url")
    private String releasesUrl;

    @JSONField(name = "deployments_url")
    private String deploymentsUrl;

    @JSONField(name = "created_at")
    private String createdAt;

    @JSONField(name = "updated_at")
    private String updatedAt;

    @JSONField(name = "pushedAt")
    private String pushed_at;

    @JSONField(name = "git_url")
    private String gitUrl;

    @JSONField(name = "ssh_url")
    private String sshUrl;

    @JSONField(name = "clone_url")
    private String cloneUrl;

    @JSONField(name = "svn_url")
    private String svnUrl;

    @JSONField(name = "homepage")
    private String homepage;

    @JSONField(name = "size")
    private long size;

    @JSONField(name = "stargazers_count")
    private long stargazersCount;

    @JSONField(name = "watchers_count")
    private long watchersCount;

    @JSONField(name = "language")
    private String language;

    @JSONField(name = "has_issues")
    private boolean hasIssues;

    @JSONField(name = "has_projects")
    private boolean hasProjects;

    @JSONField(name = "has_downloads")
    private boolean hasDownloads;

    @JSONField(name = "has_wiki")
    private boolean hasWiki;

    @JSONField(name = "has_pages")
    private boolean hasPages;

    @JSONField(name = "forks_count")
    private long forksCount;

    @JSONField(name = "mirror_url")
    private String mirrorUrl;

    @JSONField(name = "archived")
    private boolean archived;

    @JSONField(name = "disabled")
    private boolean disabled;

    @JSONField(name = "openIssuesCount")
    private long openIssuesCount;

    @JSONField(name = "license")
    private String license;

    @JSONField(name = "forks")
    private long forks;

    @JSONField(name = "open_issues")
    private long openIssues;

    @JSONField(name = "watchers")
    private long watchers;

    @JSONField(name = "default_branch")
    private String defaultBranch;

    // field permissions ignored

}