package org.luncert.portal.repos.mongo;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepos extends MongoRepository<User, ObjectId> {

    User findByAccount(String account);

}