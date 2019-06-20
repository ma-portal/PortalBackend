package org.luncert.portal.repos.mongo;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepos extends MongoRepository<User, ObjectId> {

    /**
     * 不返回密码
     * @param account
     * @return
     */
    @Query(fields = "{\"account\": 1, \"roles\": 1, \"realName\": 1, \"classOf\": 1" +
        ", \"joinTime\": 1, \"description\": 1, \"tags\": 1, \"email\": 1" +
        ", \"qq\": 1, \"phone\": 1, \"avatar\": 1, \"lastAccess\": 1}")
    User findByAccount(String account);

    User findAllByAccount(String account);

}