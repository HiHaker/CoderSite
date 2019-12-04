package com.ynu.codersite.entity.mogoentity;

import com.ynu.codersite.entity.RelationNode;

import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
public class Question {

    // 问题的id 添加唯一索引
    private String qId;
    // 用户ID
    private String userId;
    // 发表时间
    private String postTime;
    // 问题的图片
    private List<String> images;
    // 用户点赞
    private List<RelationNode> likes;
    // 用户收藏
    private List<RelationNode> favorites;

    public Question(){

    }

    public Question(String qId, String userId,
                    String postTime, List<String> images,
                    List<RelationNode> likes, List<RelationNode> favorites) {
        this.qId = qId;
        this.userId = userId;
        this.postTime = postTime;
        this.images = images;
        this.likes = likes;
        this.favorites = favorites;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<RelationNode> getLikes() {
        return likes;
    }

    public void setLikes(List<RelationNode> likes) {
        this.likes = likes;
    }

    public List<RelationNode> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<RelationNode> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qId='" + qId + '\'' +
                ", userId='" + userId + '\'' +
                ", postTime='" + postTime + '\'' +
                ", images=" + images +
                ", likes=" + likes +
                ", favorites=" + favorites +
                '}';
    }
}