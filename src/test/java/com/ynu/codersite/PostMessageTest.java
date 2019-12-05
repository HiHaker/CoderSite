package com.ynu.codersite;

import com.ynu.codersite.entity.ContentNode;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.service.APostMessageService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@SpringBootTest
public class PostMessageTest {

    @Autowired
    APostMessageService aPostMessageService;
    @Autowired
    PostMessageService postMessageService;


    @Test
    // 发表帖子测试
    void testPostPost(){
        PostMessageDTO data = new PostMessageDTO();
        data.setAid("001");
        data.setUid("001");
        data.setTitle("欢迎来到程序员的世界");
        data.setPostTime("2019-12-04");
        List<ContentNode> data1 = new ArrayList<>();
        data1.add(new ContentNode("你好","001"));
        data.setContent(data1);
        List<String> data2 = new ArrayList<>();
        data2.add("欢迎");
        data.setLabels(data2);
        aPostMessageService.postPostMessage(data);
    }

    @Test
    // 删除帖子测试
    void testDeletePostById(){
        aPostMessageService.deleteById("001");
    }

    // 失败记录1-字段没能设置为id，使用JPA删除时找不到属性，未能删除成功

    @Test
    // 子测试-删除mongo数据库
    void testDeletePostMongo(){
        postMessageService.deletePostMessageById("5de7bd73ca00733b860e1a74");
    }

    @Test
    // 增加一条点赞记录测试
    void testAddLikes(){
        aPostMessageService.addLikes("001","001","001","2019-12-5");
    }

    @Test
    // 子测试
    void testGetById(){
        System.out.println(postMessageService.getPostMessageById("001"));
    }

    // 失败记录，没有考虑刚开始时子文档likes没有对象为空值的情况，造成了空指针错误

}