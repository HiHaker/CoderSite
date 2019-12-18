package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.service.AUserService;
import com.ynu.codersite.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/12/18 0018
 * BY Jianlong
 */
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    AUserService aUserService;

    @ApiOperation(value = "获取最新消息", notes = "获取最新消息")
    @RequestMapping(value = "/getNewMessage", method = RequestMethod.GET)
    public JSONObject getNewMessages(
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("messages",messageService.getNewMessages(uid, time));
        } else{
            msg.put("code",-1);
            msg.put("message","获取失败，用户不存在");
        }
        return msg;
    }
}
