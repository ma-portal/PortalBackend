package org.luncert.portal.model.mongo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "StaticResource")
public class StaticResource implements Serializable {

    private static final long serialVersionUID = -2094185891208648305L;

    @Id
    @JsonIgnore
    private ObjectId id;

    private String type;
    private byte[] data;

}