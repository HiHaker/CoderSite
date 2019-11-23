package com.ynu.codersite.entity;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
public class AdminUser {
    // 管理员id
    private String adminId;
    // 管理员名
    private String nickname;
    // 管理员密码
    private String password;
    // 注册日期
    private String registerDate;

    public AdminUser(){

    }

    public AdminUser(String adminId, String nickname, String password, String registerDate) {
        this.adminId = adminId;
        this.nickname = nickname;
        this.password = password;
        this.registerDate = registerDate;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "adminId='" + adminId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", registerDate='" + registerDate + '\'' +
                '}';
    }
}
