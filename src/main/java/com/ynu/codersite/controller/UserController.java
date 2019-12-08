package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.UserDTO;
import com.ynu.codersite.service.AUserService;
import com.ynu.codersite.service.mongoservice.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AUserService aUserService;
    @Autowired
    UserService userService;

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParam(name = "userDTO", value = "用户注册参数信息", required = true, dataType = "UserDTO")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject userRegister(
            @RequestBody UserDTO userDTO
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(userDTO.getUserId())){
            msg.put("code",-1);
            msg.put("message","注册失败，用户已经存在");
        } else{
            aUserService.addUser(userDTO);
            msg.put("code",0);
            msg.put("message","注册成功");
        }
        return msg;
    }



    /**
     * 用户登录
     * @param uid
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject userLogin(
            @RequestParam String uid,
            @RequestParam String password
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            if (aUserService.passwordIsCorrect(uid, password)){
                msg.put("code",0);
                msg.put("message","登录成功");
            } else{
                msg.put("code",-1);
                msg.put("message","登录失败，密码错误");
            }
        } else{
            msg.put("code",-1);
            msg.put("message","登录失败，用户不存在");
        }
        return msg;
    }

    /**
     * 根据id删除用户
     * @param uid
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public JSONObject deleteUser(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            aUserService.deleteUser(uid);
            msg.put("code",0);
            msg.put("message","删除成功");
        } else{
            msg.put("code",-1);
            msg.put("message","删除失败，用户不存在");
        }
        return msg;
    }

    /**
     * 修改用户资料
     * @param uid
     * @param nickname
     * @param sex
     * @param avatarId
     * @param signature
     * @param mailbox
     * @param labels
     * @return
     */
    @ApiOperation(value = "修改用户资料", notes = "修改用户资料")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateUser(
            @RequestParam String uid,
            @RequestParam String nickname,
            @RequestParam boolean sex,
            @RequestParam String avatarId,
            @RequestParam String signature,
            @RequestParam String mailbox,
            @RequestParam List<String> labels
    ){
        JSONObject msg = new JSONObject();
        try {
            aUserService.updateUser(uid, nickname, sex, avatarId, signature, mailbox, labels);
            msg.put("code",0);
            msg.put("message","更新成功");
        } catch (NullPointerException n){
            n.printStackTrace();
            msg.put("code",-1);
            msg.put("message","更新失败，用户不存在");
        }
        return msg;
    }

    /**
     * 增加用户关注
     * @param uid
     * @param objId
     * @return
     */
    @ApiOperation(value = "增加用户关注记录", notes = "增加用户关注记录")
    @RequestMapping(value = "/addFollow", method = RequestMethod.POST)
    public JSONObject addFollow(
            @RequestParam String uid,
            @RequestParam String objId
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            if (aUserService.userIsExist(objId)){
                userService.addFollow(uid, objId);
                msg.put("code",0);
                msg.put("message","增加成功");
            } else{
                msg.put("code",-1);
                msg.put("message","被关注用户不存在");
            }
        } else{
            msg.put("code",-1);
            msg.put("message","关注用户不存在");
        }
        return msg;
    }

    /**
     * 删除用户关注
     * @param uid
     * @param objId
     * @return
     */
    @ApiOperation(value = "删除用户关注记录", notes = "删除用户关注记录")
    @RequestMapping(value = "/deleteFollow", method = RequestMethod.DELETE)
    public JSONObject deleteFollow(
            @RequestParam String uid,
            @RequestParam String objId
    ){
        JSONObject msg = new JSONObject();
        userService.deleteFollow(uid, objId);
        msg.put("code",0);
        msg.put("message","增加成功");
        return msg;
    }

    /**
     * 根据id查询用户
     * @param uid
     * @return
     */
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public JSONObject getUserById(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        UserDTO userDTO = aUserService.getUserById(uid);
        msg.put("code",0);
        msg.put("userInfo",userDTO);
        return msg;
    }

    @ApiOperation(value = "获取用户的全部关注", notes = "获取用户的全部关注")
    @RequestMapping(value = "/getAllFollows", method = RequestMethod.GET)
    public JSONObject getAllFollows(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("attents",aUserService.getAllFollows(uid));
        } else{
            msg.put("code",-1);
            msg.put("message","用户不存在");
        }
        return msg;
    }

    @ApiOperation(value = "获取用户的全部粉丝", notes = "获取用户的全部粉丝")
    @RequestMapping(value = "/getAllFans", method = RequestMethod.GET)
    public JSONObject getAllFans(
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("attents",aUserService.getAllFans(uid));
        } else{
            msg.put("code",-1);
            msg.put("message","用户不存在");
        }
        return msg;
    }
}