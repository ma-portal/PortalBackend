package org.luncert.portal.model.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luncert.portal.model.mongo.User;
import org.luncert.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void testSignin() throws Exception {
        authorize();
    }

    public MockHttpSession authorize() throws Exception {
        String credential = new StringBuilder().append(URLEncoder.encode("account", "UTF-8")).append('=')
                .append(URLEncoder.encode("admin", "UTF-8")).append('&').append(URLEncoder.encode("password", "UTF-8"))
                .append('=').append(URLEncoder.encode("123456", "UTF-8")).toString();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user/signin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).content(credential));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\"identified\":true}"));

        MvcResult result = resultActions.andReturn();
        MockHttpServletRequest req = result.getRequest();
        return (MockHttpSession) req.getSession();
    }

    @Test
    public void testGetAvatar() throws Exception {
        String avatarUri = userService.getAvatar("admin");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/avatar/{account}", "admin"));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\"avatar\":\"" + avatarUri + "\"}"));
    }

    @Test
    public void testUpdateAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("avatar", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE,
                this.getClass().getResourceAsStream("avatar.jpg"));
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.multipart("/user/avatar").file(file).session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted());

        MvcResult result = resultActions.andReturn();
        byte[] data = result.getResponse().getContentAsByteArray();
        JSONObject ret = JSON.parseObject(new String(data));
        Assert.assertNotNull(ret.getString("avatar"));
    }

    @Test
    public void testGetProfile() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateProfile() throws Exception {
        User user = User.builder().password("123456").classOf(2016).joinTime(System.currentTimeMillis())
                .tags(Arrays.nonNullElementsIn(new String[] { "LOL", "JAVA", "Golang" })).realName("Cruska Li")
                .description("hahahahaha...").email("seeyouin@mobileai.com").qq("13989351543").phone("13989351543")
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/user/profile")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(user)).session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void testUpdateRole() throws Exception {
        // add role
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/user/role/{roleName}", "Admin")
                .param("targetAccount", "test").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted());

        // remove role
        resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/user/role/{roleName}", "Admin")
                .param("targetAccount", "test").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void testManageUser() throws Exception {
        // add user
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/user").param("account", "test+").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        // delete user
        resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/user").param("account", "test+").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * This test is not accompleted
     * @throws Exception
     */
    @Test
    public void testGetProject() throws Exception {
        // this request will be interupted by GitlabFilter
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/project").session(authorize()));
        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        String location = resultActions.andReturn().getResponse().getHeader("Location");
        Assert.assertNotNull("location should be non-null", location);

        // send auth code to GitlabController
        String state = substractParam(location, "state");
        Assert.assertNotNull("state should be non-null", state);
        // resultActions = mockMvc.perform(
        //     MockMvcRequestBuilders.get("/gitlab/redirect?code={code}&state={state}", null, state)
        //         .session(authorize()));
        // resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        // GitlabController will invoke GitlabService to retrive access token with auth code, and redirect user to the real page he requests.
    }

    private String substractParam(String url, String name) throws MalformedURLException {
        URL u = new URL(url);
        Map<String, String> params = new HashMap<>();
        for (String pair : u.getQuery().split("&")) {
            String[] tmp = pair.split("=");
            params.put(tmp[0], tmp[1]);
        }
        return params.get(name);
    }

}