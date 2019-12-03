package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.service.mongoservice.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "nickname", value = "用户昵称"),
            @ApiImplicitParam(name = "password", value = "账号密码", required = true),
            @ApiImplicitParam(name = "birthday", value = "生日"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "registerDate", value = "注册日期", required = true),
            @ApiImplicitParam(name = "avatarId", value = "头像id"),
            @ApiImplicitParam(name = "signature", value = "个性签名"),
            @ApiImplicitParam(name = "mailbox", value = "邮箱"),
            @ApiImplicitParam(name = "coverPicture", value = "背景图像", required = true),
            @ApiImplicitParam(name = "labels", value = "用户标签", dataType = "String", allowMultiple = true),
            @ApiImplicitParam(name = "follows", value = "我的关注", dataType = "String", allowMultiple = true)
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject userRegister(User user){
        JSONObject msg = new JSONObject();
        User temp = userService.getUserById(user.getUserId());
        if (temp == null){
            userService.addUser(user);
            msg.put("success","注册成功");
        } else {
            msg.put("error","注册失败，用户已经存在");
        }
        return msg;
    }

    /**
     * 用户登录
     * @param userId
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "password", value = "账号密码", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject userLogin(
            @RequestParam String userId,
            @RequestParam String password
    ){
        JSONObject msg = new JSONObject();
        User temp = userService.getUserById(userId);
        if (temp == null){
            msg.put("error", "用户不存在");
        } else {
            if (!temp.getPassword().equals(password)){
                msg.put("error", "密码错误");
            } else {
                msg.put("success", "登录成功");
            }
        }
        return msg;
    }
}