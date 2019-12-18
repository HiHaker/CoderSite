package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.mogoentity.ChatList;
import com.ynu.codersite.service.AUserService;
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
    @Autowired
    AUserService aUserService;

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
    @RequestMapping(value = "/getMyChatList", method = RequestMethod.GET)
    public JSONObject getChatList(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("chatList",chatListService.getMyChatList(uid));
        } else{
            msg.put("code",-1);
            msg.put("message","获取失败，用户不存在");
        }
        return msg;
    }

    @ApiOperation(value = "获取当前用户和某用户的私信", notes = "获取当前用户和某用户的私信列表")
    @RequestMapping(value = "/getMyChatListWithUser", method = RequestMethod.GET)
    public JSONObject getMyChatListWithUser(
            @RequestParam String uid,
            @RequestParam String objId
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("chatList", chatListService.getMyChatListWithUser(uid, objId));
        return msg;
    }

}