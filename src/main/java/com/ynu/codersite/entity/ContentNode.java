package com.ynu.codersite.entity;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
public class ContentNode {
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
