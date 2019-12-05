package com.ynu.codersite.entity;

import java.util.List;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
public class PostMessageDTO {
    // 帖子的id
    private String aid;
    // 用户的id
    private String uid;
    // 帖子的标题
    private String title;
    // 帖子的内容
    private List<ContentNode> content;
    // 帖子的标签
    private List<String> labels;
    // 帖子的发表时间
    private String postTime;

    public PostMessageDTO() {
    }

    public PostMessageDTO(String aid, String uid, String title, List<ContentNode> content, List<String> labels, String postTime) {
        this.aid = aid;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.labels = labels;
        this.postTime = postTime;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentNode> getContent() {
        return content;
    }

    public void setContent(List<ContentNode> content) {
        this.content = content;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
