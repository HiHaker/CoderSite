package com.ynu.codersite.entity.esentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Document(indexName = "codersite", type = "userInfo", shards = 1, replicas = 0)
public class UserInfo {
    @Id
    private String userId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String signature;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
