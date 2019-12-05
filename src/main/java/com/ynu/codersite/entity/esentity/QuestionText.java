package com.ynu.codersite.entity.esentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 * elasticsearch自带的分词器对中文非常不友好，所以我们需要安装一个ik分词器
 * @Document 注解，索引名称（数据库），类型（表），因为这里数据量小，不分片，不备份
 */
@Document(indexName = "questiontext", type = "questiontext", shards = 1, replicas = 0)
public class QuestionText {
    // 问题id
    @Id
    private String qId;
    // 问题标题，使用ik分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    // 问题的标签，使用ik分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private List<String> labels;
    // 问题的内容，使用ik分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionText(String qId, String title, List<String> labels, String content) {
        this.qId = qId;
        this.title = title;
        this.labels = labels;
        this.content = content;
    }
}