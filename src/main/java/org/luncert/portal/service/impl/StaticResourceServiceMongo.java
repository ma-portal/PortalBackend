package org.luncert.portal.service.impl;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.StaticResource;
import org.luncert.portal.repos.mongo.StaticResourceRepos;
import org.luncert.portal.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Primary
public class StaticResourceServiceMongo implements StaticResourceService {

    @Autowired
    private StaticResourceRepos repos;

    @Override
    public String save(String fileName, byte[] data) {
        Objects.requireNonNull(data, "parameter data must be non-null");

        String suffix = null;
        int i = fileName.lastIndexOf(".");
        if (i > -1) {
            suffix = fileName.substring(i + 1);
        }
        StaticResource res = StaticResource.builder()
                        .type(suffix).data(data).build();
        res = repos.save(res);

        return new ResourceId(res.getId(), res.getType()).encode();
    }

    @Override
    public boolean exists(String resId) {
        ResourceId id = ResourceId.decode(resId);
        return repos.existsById(id.getId());
    }

    @Override
    public byte[] get(String resId) {
        ResourceId id = ResourceId.decode(resId);
        StaticResource res = repos.findByIdAndType(id.getId(), id.getType());
        return res.getData();
    }

    @Override
    public void delete(String resId) {
        ResourceId id = ResourceId.decode(resId);
        repos.deleteById(id.getId());
    }

    @Data
    private static class ResourceId {
        ObjectId id;
        String type;

        ResourceId() {}

        ResourceId(ObjectId id, String type) {
            this.id = id;
            this.type = type;
        }

        /**
         * 从字符串解析出ResourceId
         * @param resId
         * @return
         */
        static ResourceId decode(String resId) {
            ResourceId tmp = new ResourceId();
            int i = resId.lastIndexOf(".");
            if (i > -1) {
                tmp.setId(new ObjectId(resId.substring(0, i)));
                tmp.setType(resId.substring(i + 1));
            } else {
                tmp.setId(new ObjectId(resId));
            }
            return tmp;
        }

        /**
         * 编码成字符串
         * @return e.g. OIJ-OJDONASD.jpg
         */
        String encode() {
            return type == null ?
                id.toHexString() : id.toHexString() + "." + type;
        }
    }

}