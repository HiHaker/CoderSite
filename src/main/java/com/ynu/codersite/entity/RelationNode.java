package com.ynu.codersite.entity;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
public class RelationNode {
    private String userId;
    private String time;

    public RelationNode(){

    }

    public RelationNode(String userId, String time) {
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
