package com.ynu.codersite.entity;

import com.ynu.codersite.entity.mogoentity.TextNode;

/**
 * Created on 2019/12/18 0018
 * BY Jianlong
 */
public class MessageNode {
    private String uid;
    private String avatar;
    private String nickname;
    private String time;
    private boolean isAttent;
    private String postType;
    private TextNode post;
    private String messageType;
    private String comment;
    private String answer;

    public MessageNode() {
    }

    public MessageNode(String uid, String avatar, String nickname,
                        String time, boolean isAttent, String postType,
                        TextNode post, String messageType, String comment, String answer) {
        this.uid = uid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.time = time;
        this.isAttent = isAttent;
        this.postType = postType;
        this.post = post;
        this.messageType = messageType;
        this.comment = comment;
        this.answer = answer;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isAttent() {
        return isAttent;
    }

    public void setAttent(boolean attent) {
        isAttent = attent;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public TextNode getPost() {
        return post;
    }

    public void setPost(TextNode post) {
        this.post = post;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "GMessageNode{" +
                "uid='" + uid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", time='" + time + '\'' +
                ", isAttent=" + isAttent +
                ", postType='" + postType + '\'' +
                ", post=" + post +
                ", messageType='" + messageType + '\'' +
                ", comment='" + comment + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
