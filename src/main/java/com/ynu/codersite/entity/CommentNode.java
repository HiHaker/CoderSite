package com.ynu.codersite.entity;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
public class CommentNode {
    String userId;
    String content;
    String time;

    public CommentNode() {
    }

    public CommentNode(String userId, String content, String time) {
        this.userId = userId;
        this.content = content;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
