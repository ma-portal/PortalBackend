package org.luncert.portal.model.gitlab;

import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProjectTests {

    @Test
    public void test() {
        String mockData = "{\"id\":1,\"description\":\"\",\"name\":\"TestProject\",\"name_with_namespace\":\"Administrator / TestProject\",\"path\":\"testproject\",\"path_with_namespace\":\"root/testproject\",\"created_at\":\"2019-06-16T11:56:11.832Z\",\"default_branch\":\"master\",\"tag_list\":[],\"ssh_url_to_repo\":\"git@c62dcaa1c9cb:root/testproject.git\",\"http_url_to_repo\":\"http://c62dcaa1c9cb/root/testproject.git\",\"web_url\":\"http://c62dcaa1c9cb/root/testproject\",\"readme_url\":\"http://c62dcaa1c9cb/root/testproject/blob/master/README.md\",\"avatar_url\":null,\"star_count\":0,\"forks_count\":0,\"last_activity_at\":\"2019-06-16T11:56:11.832Z\",\"namespace\":{\"id\":1,\"name\":\"root\",\"path\":\"root\",\"kind\":\"user\",\"full_path\":\"root\",\"parent_id\":null,\"avatar_url\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\u0026d=identicon\",\"web_url\":\"http://c62dcaa1c9cb/root\"},\"_links\":{\"self\":\"http://c62dcaa1c9cb/api/v4/projects/1\",\"issues\":\"http://c62dcaa1c9cb/api/v4/projects/1/issues\",\"merge_requests\":\"http://c62dcaa1c9cb/api/v4/projects/1/merge_requests\",\"repo_branches\":\"http://c62dcaa1c9cb/api/v4/projects/1/repository/branches\",\"labels\":\"http://c62dcaa1c9cb/api/v4/projects/1/labels\",\"events\":\"http://c62dcaa1c9cb/api/v4/projects/1/events\",\"members\":\"http://c62dcaa1c9cb/api/v4/projects/1/members\"},\"archived\":false,\"visibility\":\"public\",\"owner\":{\"id\":1,\"name\":\"Administrator\",\"username\":\"root\",\"state\":\"active\",\"avatar_url\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\u0026d=identicon\",\"web_url\":\"http://c62dcaa1c9cb/root\"},\"resolve_outdated_diff_discussions\":false,\"container_registry_enabled\":true,\"issues_enabled\":true,\"merge_requests_enabled\":true,\"wiki_enabled\":true,\"jobs_enabled\":true,\"snippets_enabled\":true,\"shared_runners_enabled\":true,\"lfs_enabled\":true,\"creator_id\":1,\"import_status\":\"none\",\"open_issues_count\":0,\"public_jobs\":true,\"ci_config_path\":null,\"shared_with_groups\":[],\"only_allow_merge_if_pipeline_succeeds\":false,\"request_access_enabled\":false,\"only_allow_merge_if_all_discussions_are_resolved\":false,\"printing_merge_request_link_enabled\":true,\"merge_method\":\"merge\",\"external_authorization_classification_label\":null,\"permissions\":{\"project_access\":{\"access_level\":40,\"notification_level\":3},\"group_access\":null}}";
        
        Assert.assertNotNull("JSON parsing failed",
            JSONObject.parseObject(mockData, Project.class));
    }

}