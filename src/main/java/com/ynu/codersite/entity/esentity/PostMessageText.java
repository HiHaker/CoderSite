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
@Document(indexName = "codersite", type = "postMessageText", shards = 1, replicas = 0)
public class PostMessageText {
    private class ContentNode{
        String para;
        String image;

        public ContentNode() {
        }

        public ContentNode(String para, String image) {
            this.para = para;
            this.image = image;
        }

        public String getPara() {
            return para;
        }

        public void setPara(String para) {
            this.para = para;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    private class CommentNode{
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

    @Id
    private String pId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private List<ContentNode> content;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private List<CommentNode> comments;

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

    @Override
    public String toString() {
        return "PostMessageText{" +
                "pId='" + pId + '\'' +
                ", title='" + title + '\'' +
                ", content=" + content +
                ", comments=" + comments +
                '}';
    }
}
