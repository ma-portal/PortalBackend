package org.luncert.portal.repository.mongo;

import org.bson.types.ObjectId;
import org.luncert.portal.model.mongo.StaticResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticResourceRepo extends MongoRepository<StaticResource, ObjectId> {

    StaticResource findByIdAndType(ObjectId id, String type);

}