package com.ynu.codersite.entity.esentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Document(indexName = "userinfo", type = "userInfo", shards = 1, replicas = 0)
public class UserInfo {
    @Id
    private String userId;
    // 用户的昵称
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String nickname;
    // 用户的个性签名
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String signature;
    // 用户的标签
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private List<String> labels;

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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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
