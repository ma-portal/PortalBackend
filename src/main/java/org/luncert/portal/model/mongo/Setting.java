package org.luncert.portal.model.mongo;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Setting")
public class Setting implements Serializable {

    private static final long serialVersionUID = 2216276013238733499L;

    @Id
    ObjectId id;

    private String backgroundImage;
    private String language;

}