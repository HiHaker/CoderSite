package com.ynu.codersite.entity;

import java.util.List;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
public class QuestionDTO {
    // 问题的id 添加唯一索引
    private String qid;
    // 用户ID
    private String uid;
    // 发表时间
    private String postTime;
    // 问题的标签
    private List<String> labels;
    // 问题的标题
    private String title;
    // 问题的内容
    private String content;
    // 问题的图片
    private List<String> images;

    public QuestionDTO(){

    }

    public QuestionDTO(String qid, String uid,
                       String postTime, List<String> labels,
                       String title, String content, List<String> images) {
        this.qid = qid;
        this.uid = uid;
        this.postTime = postTime;
        this.labels = labels;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "qid='" + qid + '\'' +
                ", uid='" + uid + '\'' +
                ", postTime='" + postTime + '\'' +
                ", labels=" + labels +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", images=" + images +
                '}';
    }
}
