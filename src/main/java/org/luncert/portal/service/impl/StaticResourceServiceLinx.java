package org.luncert.portal.service.impl;

import org.luncert.portal.service.StaticResourceService;
import org.springframework.stereotype.Service;

/**
 * TODO: implementation
 */
@Deprecated
@Service
public class StaticResourceServiceLinx implements StaticResourceService {

    @Override
    public String save(String fileName, byte[] data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists(String resId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] get(String resId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String resId) {
        throw new UnsupportedOperationException();
    }

}