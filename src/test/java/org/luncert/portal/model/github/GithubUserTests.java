package org.luncert.portal.model.github;

import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GithubUserTests {

    @Test
    public void test() {
        String mockData = "{\"login\":\"ChrisCN97\",\"id\":37807308,\"node_id\":\"MDQ6VXNlcjM3ODA3MzA4\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/37807308?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/ChrisCN97\",\"html_url\":\"https://github.com/ChrisCN97\",\"followers_url\":\"https://api.github.com/users/ChrisCN97/followers\",\"following_url\":\"https://api.github.com/users/ChrisCN97/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/ChrisCN97/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/ChrisCN97/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/ChrisCN97/subscriptions\",\"organizations_url\":\"https://api.github.com/users/ChrisCN97/orgs\",\"repos_url\":\"https://api.github.com/users/ChrisCN97/repos\",\"events_url\":\"https://api.github.com/users/ChrisCN97/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/ChrisCN97/received_events\",\"type\":\"User\",\"site_admin\":false}";
        
        Assert.assertNotNull("JSON parsing failed",
            JSONObject.parseObject(mockData, GithubUser.class));
    }

}