package org.luncert.portal.service.impl;

import org.luncert.portal.service.StaticResourceService;
import org.springframework.stereotype.Service;

@Service
public class StaticResourceServiceLinx implements StaticResourceService {

    @Override
    public String save(String fileName, byte[] data) {
        return null;
    }

    @Override
    public boolean exists(String resId) {
        return false;
    }

    @Override
    public byte[] get(String resId) {
        return null;
    }

    @Override
    public void delete(String resId) {
    }

}