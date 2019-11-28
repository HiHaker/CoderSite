package com.ynu.codersite.entity;

import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
public class PostMessage {

    private class rNode{
        private String userId;
        private String time;

        public rNode(){

        }

        public rNode(String userId, String time) {
            this.userId = userId;
            this.time = time;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    // 帖子的id
    private String pId;
    // 用户的id
    private String userId;
    // 帖子的发表时间
    private String postTime;
    // 帖子的标签
    private List<String> labels;
    // 帖子的图片
    private List<String> images;
    // 用户点赞
    private List<rNode> likes;
    // 用户收藏
    private List<rNode> favorites;

    public PostMessage(){

    }

    public PostMessage(String pId, String userId,
                       String postTime, List<String> labels,
                       List<String> images, List<rNode> likes,
                       List<rNode> favorites) {
        this.pId = pId;
        this.userId = userId;
        this.postTime = postTime;
        this.labels = labels;
        this.images = images;
        this.likes = likes;
        this.favorites = favorites;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<rNode> getLikes() {
        return likes;
    }

    public void setLikes(List<rNode> likes) {
        this.likes = likes;
    }

    public List<rNode> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<rNode> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "PostMessage{" +
                "pId='" + pId + '\'' +
                ", userId='" + userId + '\'' +
                ", postTime='" + postTime + '\'' +
                ", labels=" + labels +
                ", images=" + images +
                ", likes=" + likes +
                ", favorites=" + favorites +
                '}';
    }
}