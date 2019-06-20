package org.luncert.portal.model.gitlab;

import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GitlabUserTests {

    @Test
    public void test() {
        String mockData = "{\"id\":1,\"name\":\"Administrator\",\"username\":\"root\",\"state\":\"active\",\"avatar_url\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\u0026d=identicon\",\"web_url\":\"http://c62dcaa1c9cb/root\",\"access_level\":40,\"expires_at\":null}";
        
        Assert.assertNotNull("JSON parsing failed",
            JSONObject.parseObject(mockData, GitlabUser.class));
    }

}