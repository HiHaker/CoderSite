package com.ynu.codersite.entity.mogoentity;

/**
 * Created on 2019/12/18 0018
 * BY Jianlong
 */
public class TextNode {
    private String id;
    private String image;
    private String para;

    public TextNode() {
    }

    public TextNode(String id, String image, String para) {
        this.id = id;
        this.image = image;
        this.para = para;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    @Override
    public String toString() {
        return "TextNode{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", para='" + para + '\'' +
                '}';
    }
}