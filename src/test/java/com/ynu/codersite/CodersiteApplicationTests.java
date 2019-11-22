package com.ynu.codersite;

import com.ynu.codersite.entity.User;
import com.ynu.codersite.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CodersiteApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void testInsertUser(){
        User u = new User();
        u.setUserId("001");
        u.setNickname("Jianlong");
        u.setPassword("123456");
        u.setBirthday("1999-05-29");
        u.setSex(true);
        u.setRegisterDate("2019-11-22");
        u.setAvatarId("");
        u.setDescription("系统创建人");
        u.setMailbox("1434938035@qq.com");
        List<String> labels = new ArrayList<>();
        labels.add("男性");
        u.setLabels(labels);
        List<String> follows = new ArrayList<>();
        u.setFollows(follows);

        userService.addUser(u);
    }

    @Test
    void testGetUserById(){
        System.out.println(userService.getUserById("001"));
    }

    @Test
    void contextLoads() {
    }

}
