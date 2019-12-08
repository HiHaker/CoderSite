package com.ynu.codersite.entity;

import java.util.List;

/**
 * Created on 2019/12/8 0008
 * BY Jianlong
 */
public class UserDTO {
    private String userId;
    private String nickname;
    private String password;
    private String birthday;
    private boolean sex;
    private String registerDate;
    private String avatarId;
    private String signature;
    private String mailbox;
    private String coverPicture;
    private List<String> labels;

    public UserDTO() {
    }

    public UserDTO(String userId, String nickname,
                   String password, String birthday,
                   boolean sex, String registerDate,
                   String avatarId, String signature,
                   String mailbox, String coverPicture,
                   List<String> labels) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.registerDate = registerDate;
        this.avatarId = avatarId;
        this.signature = signature;
        this.mailbox = mailbox;
        this.coverPicture = coverPicture;
        this.labels = labels;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", registerDate='" + registerDate + '\'' +
                ", avatarId='" + avatarId + '\'' +
                ", signature='" + signature + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", labels=" + labels +
                '}';
    }
}
