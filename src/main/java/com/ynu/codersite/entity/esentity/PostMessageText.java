package com.ynu.codersite.entity.esentity;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.ContentNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Document(indexName = "postmessagetext", type = "postMessageText", shards = 1, replicas = 0)
public class PostMessageText {
    @Id
    private String pId;
    // 帖子的标题
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    // 帖子的标签
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private List<String> labels;
    // 帖子的内容
   @Field(type = FieldType.Nested, analyzer = "ik_max_word")
    private List<ContentNode> content;
   // 帖子的评论
    @Field(type = FieldType.Nested, analyzer = "ik_max_word")
    private List<CommentNode> comments;
    // 发表时间
    @Field(type = FieldType.Date)
    private String postTime;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<ContentNode> getContent() {
        return content;
    }

    public void setContent(List<ContentNode> content) {
        this.content = content;
    }

    public List<CommentNode> getComments() {
        return comments;
    }

    public void setComments(List<CommentNode> comments) {
        this.comments = comments;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "PostMessageText{" +
                "pId='" + pId + '\'' +
                ", title='" + title + '\'' +
                ", labels=" + labels +
                ", content=" + content +
                ", comments=" + comments +
                ", postTime='" + postTime + '\'' +
                '}';
    }
}
