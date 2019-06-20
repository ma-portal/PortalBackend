package org.luncert.portal.model.gitlab;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * Gitlab - Project实体
 */
@Data
public class Project {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "name_with_namespace")
    private String nameWithNamespace;

    @JSONField(name = "path")
    private String path;

    @JSONField(name = "path_with_namespace")
    private String pathWithNamespace;

    @JSONField(name = "created_at")
    private String createdAt;

    @JSONField(name = "default_branch")
    private String defaultBranch;

    @JSONField(name = "tag_list")
    private List<String> tagList;

    @JSONField(name = "ssh_url_to_repo")
    private String sshUrlToRepo;

    @JSONField(name = "http_url_to_repo")
    private String httpUrlToRepo;

    @JSONField(name = "web_url")
    private String webUrl;

    @JSONField(name = "readme_url")
    private String readmeUrl;

    @JSONField(name = "avatar_url")
    private String avatarUrl;

    @JSONField(name = "star_count")
    private int startCount;

    @JSONField(name = "forks_count")
    private int forksCount; 

    @JSONField(name = "last_activity_at")
    private String lastActivityAt;

    // namespace字段被舍弃了

    /* 以下字段是授权后才能看到的字段 */

    // _links字段被舍弃了

    @JSONField(name = "archived")
    private boolean archived;

    @JSONField(name = "visibility")
    private String visibility;

    // owner字段被舍弃了

    // @JSONField(name = "resolve_outdated_diff_discussions")
    // private boolean resolveOutdatedDiffDiscussions;

    // @JSONField(name = "container_registry_enabled")
    // private boolean containerRegistryEnabled;

    @JSONField(name = "issues_enabled")
    private boolean issuesEnabled;

    @JSONField(name = "merge_requests_enabled")
    private boolean mergeRequestsEnabled;

    @JSONField(name = "wiki_enabled")
    private boolean wikiEnabled;

    @JSONField(name = "jobs_enabled")
    private boolean jobsEnabled;

    // @JSONField(name = "snippets_enabled")
    // private boolean snippetsEnabled;
    
    // @JSONField(name = "shared_runners_enabled")
    // private boolean sharedRunnersEnabled;

    // @JSONField(name = "lfs_enabled")
    // private boolean lfsEnabled;

    @JSONField(name = "creator_id")
    private int creatorId;

    @JSONField(name = "import_status")
    private String importStatus;

    @JSONField(name = "open_issue_count")
    private int openIssuesCount;

    // @JSONField(name = "public_jobs")
    // private boolean publicJobs;

    // @JSONField(name = "ci_config_path")
    // private String ciConfigPath;

    // @JSONField(name = "shared_with_groups")
    // private List<String> sharedWithGroups;

    // @JSONField(name = "only_allow_merge_if_pipeline_succeeds")
    // private boolean onlyAllowMergeIfPipelineSucceeds;

    // @JSONField(name = "request_access_enabled")
    // private boolean requestAccessEnabled;

    // @JSONField(name = "only_allow_merge_if_all_discussions_are_resolved")
    // private boolean onlyAllowMergeIfAllDiscussionsAreResolved;

    // @JSONField(name = "printing_merge_request_link_enabled")
    // private boolean printingMergeRequestLinkEnabled;

    // @JSONField(name = "merge_method")
    // private String mergeMethod;

    // @JSONField(name = "external_authorization_classification_label")
    // private String externalAuthorizationClassificationLabel;

    // permissions字段被丢弃了

}