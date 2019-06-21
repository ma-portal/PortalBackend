package org.luncert.portal.model.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luncert.portal.service.impl.StaticResourceServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaticResourceServiceMongoTests {

    @Autowired
    private StaticResourceServiceMongo service;

    private static String mockFileName = "test.jpg";
    private static byte[] mockFileData = "test data".getBytes();

    @Test
    public void test() throws Exception {
        String resId = service.save(mockFileName, mockFileData);
        subTest(resId);

        resId = service.save(mockFileName, new ByteArrayInputStream(mockFileData));
        subTest(resId);

        // no test for MultipartFile
    }

    public void subTest(String resId) {
        int i = resId.lastIndexOf(".");
        Assert.assertTrue("invalid resource id: " + resId, i > 0);

        String type = resId.substring(i + 1);
        Assert.assertTrue("invalid resource type: " + type, type.equals("jpg"));

        // exists
        Assert.assertTrue("true expected as resouce has been saved", service.exists(resId));

        // get
        byte[] data = service.get(resId);
        Assert.assertTrue("data returned not equal to mock data", Arrays.equals(data, mockFileData));

        // delete
        service.delete(resId);

        Assert.assertFalse("false expected as resouce has been deleted", service.exists(resId));
    }

}