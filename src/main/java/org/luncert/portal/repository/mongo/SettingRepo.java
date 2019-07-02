package org.luncert.portal.repository.mongo;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepo extends MongoRepository<Setting, ObjectId> {

}