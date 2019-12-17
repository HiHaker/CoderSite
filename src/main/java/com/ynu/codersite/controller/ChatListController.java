package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.mogoentity.ChatList;
import com.ynu.codersite.service.mongoservice.ChatListService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/12/17 0017
 * BY Jianlong
 */
@CrossOrigin
@RestController
@RequestMapping("/chatList")
public class ChatListController {
    @Autowired
    ChatListService chatListService;

    @ApiOperation(value = "发表私信", notes = "发表私信")
    @ApiImplicitParam(name = "chatList", value = "用户注册参数信息", required = true, dataType = "ChatList")
    @RequestMapping(value = "/addChat", method = RequestMethod.POST)
    public JSONObject addChat(
            @RequestBody ChatList chatList
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("message","success");
        chatListService.addChat(chatList);
        return msg;
    }

    @ApiOperation(value = "获取某用户的私信列表", notes = "获取某用户的私信列表")
    @ApiImplicitParam(name = "getChatList", value = "获取某用户的私信列表", required = true, dataType = "ChatList")
    @RequestMapping(value = "/addChat", method = RequestMethod.POST)
    public JSONObject getChatList(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        return null;
    }

}