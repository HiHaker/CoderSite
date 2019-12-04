package com.ynu.codersite.entity.mogoentity;

import java.util.List;

/**
 * Created on 2019/11/18 0018
 * BY Jianlong
 */
// 不使用注解@Document，让springboot自动建表
public class User {
    // 用户ID 添加唯一索引
    private String userId;
    // 密码 必须
    private String password;
    // 生日
    private String birthday;
    // 性别
    private boolean sex;
    // 注册日期 必须
    private String registerDate;
    // 头像Id
    private String avatarId;
    // 邮箱
    private String mailbox;
    // 背景图片
    private String coverPicture;

    // 用户的关注
    private List<String> follows;

    public User(){

    }

    public User(String userId, String password,
                String birthday, boolean sex,
                String registerDate, String avatarId,
                String mailbox, String coverPicture,
                List<String> follows) {
        this.userId = userId;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.registerDate = registerDate;
        this.avatarId = avatarId;
        this.mailbox = mailbox;
        this.coverPicture = coverPicture;
        this.follows = follows;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", registerDate='" + registerDate + '\'' +
                ", avatarId='" + avatarId + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", follows=" + follows +
                '}';
    }
}