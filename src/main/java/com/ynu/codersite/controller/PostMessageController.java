package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.service.APostMessageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@RestController
@RequestMapping("/article")
public class PostMessageController {
    @Autowired
    APostMessageService aPostMessageService;

    /**
     * 发表文章
     * @param postMessageDTO
     * @return
     */
    @ApiOperation(value = "发表文章", notes = "发表文章")
    @ApiImplicitParam(name = "postMessageDTO", value = "发表文章参数信息", required = true, dataType = "PostMessageDTO")
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public JSONObject addArticle(
            @RequestBody PostMessageDTO postMessageDTO
            ){
        JSONObject msg = new JSONObject();
        aPostMessageService.postPostMessage(postMessageDTO);
        msg.put("code","0");
        msg.put("message","发表成功");
        return msg;
    }

    @ApiOperation(value = "根据id删除文章", notes = "根据删除文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String")
    @RequestMapping(value = "/deleteArticleById", method = RequestMethod.DELETE)
    public JSONObject deleteArticleById(
            @PathVariable String id
    ){
        JSONObject msg = new JSONObject();
        aPostMessageService.deleteById(id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }


    @ApiOperation(value = "增加点赞", notes = "增加点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点赞记录id", required = true),
            @ApiImplicitParam(name = "aid", value = "文章id", required = true),
            @ApiImplicitParam(name = "uid", value = "用户id", required = true),
            @ApiImplicitParam(name = "time", value = "时间", required = true),
    })
    @RequestMapping(value = "/addLikes", method = RequestMethod.POST)
    public JSONObject addLikes(
            @RequestParam String id,
            @RequestParam String aid,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        aPostMessageService.addLikes(id,aid,uid,time);
        msg.put("code","0");
        msg.put("message","点赞成功");
        return msg;
    }



}