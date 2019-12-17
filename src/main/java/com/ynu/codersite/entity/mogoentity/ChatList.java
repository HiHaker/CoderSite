package com.ynu.codersite.entity.mogoentity;

import org.springframework.data.annotation.Id;

/**
 * Created on 2019/12/17 0017
 * BY Jianlong
 */
public class ChatList {
    @Id
    private String id;
    // 用户a
    private String uid;
    // 目标用户
    private String objId;
    // 发表内容
    private String message;
    // 发表时间
    private String time;

    public ChatList() {
    }

    public ChatList(String id, String uid, String objId, String message, String time) {
        this.id = id;
        this.uid = uid;
        this.objId = objId;
        this.message = message;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatList{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", objId='" + objId + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
