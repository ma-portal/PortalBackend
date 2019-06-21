package org.luncert.portal.model.service.impl;

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
    public void testSave() {
        String resUri = service.save(mockFileName, mockFileData);
    }

}