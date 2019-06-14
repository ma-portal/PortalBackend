package org.luncert.portal.model.mongo;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "User")
public class User implements Serializable
{

    public enum Role {
        Admin,
        Normal
    }

    private static final long serialVersionUID = -7479078315947463787L;

    @Id
    ObjectId id;
    
    private String account;
    private String password;
    private List<Role> roles;
    private String realName;
    private int classOf;
    private long joinTime;
    private String description;
    private List<String> tags;
    private String email;
    private String qq;
    private String phone;
    private String avatar;

}