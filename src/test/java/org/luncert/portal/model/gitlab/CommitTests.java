package org.luncert.portal.model.gitlab;

import com.alibaba.fastjson.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CommitTests {

    @Test
    public void test() {
        String mockData = "{\"id\":\"da8bf64feea2ab118e0e5501c5f95e65e74ff923\",\"short_id\":\"da8bf64f\",\"created_at\":\"2019-06-16T11:56:12.000Z\",\"parent_ids\":[],\"title\":\"Initial commit\",\"message\":\"Initial commit\",\"author_name\":\"Administrator\",\"author_email\":\"admin@example.com\",\"authored_date\":\"2019-06-16T11:56:12.000Z\",\"committer_name\":\"Administrator\",\"committer_email\":\"admin@example.com\",\"committed_date\":\"2019-06-16T11:56:12.000Z\"}";
        
        Assert.assertNotNull("JSON parsing failed",
            JSONObject.parseObject(mockData, Commit.class));
    }

}