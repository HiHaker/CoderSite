package com.ynu.codersite;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.mogoentity.ChatList;
import com.ynu.codersite.service.mongoservice.ChatListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/18 0018
 * BY Jianlong
 */
@SpringBootTest
public class ChatListTest {
    @Autowired
    ChatListService chatListService;

    @Test
    void getMyChatListWithUser(){
        List<JSONObject> result = chatListService.getMyChatListWithUser("001","002");
        for (JSONObject j:result){
            System.out.println(j);
        }
    }

    @Test
    void ListNullAdd(){
        List<ChatList> aList = null;
        System.out.println(aList);
        List<ChatList> bList = new ArrayList<>();
        aList.addAll(bList);
        System.out.println(bList);
    }
}