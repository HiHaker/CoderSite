package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.service.APostMessageService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
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
    @Autowired
    PostMessageService postMessageService;

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
        aPostMessageService.addPostMessage(postMessageDTO);
        msg.put("code","0");
        msg.put("message","发表成功");
        return msg;
    }

    @ApiOperation(value = "根据id删除文章", notes = "根据id删除文章")
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
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public JSONObject addLike(
            @RequestParam String id,
            @RequestParam String aid,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        try {
            postMessageService.addLike(id,aid,uid,time);
            msg.put("code","0");
            msg.put("message","点赞成功");
        } catch (NullPointerException n){
            n.printStackTrace();
            msg.put("code","-1");
            msg.put("message","点赞失败");
        } finally {
            return msg;
        }
    }

    @ApiOperation(value = "增加收藏", notes = "增加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收藏记录id", required = true),
            @ApiImplicitParam(name = "aid", value = "文章id", required = true),
            @ApiImplicitParam(name = "uid", value = "用户id", required = true),
            @ApiImplicitParam(name = "time", value = "时间", required = true),
    })
    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    public JSONObject addFavorite(
            @RequestParam String id,
            @RequestParam String aid,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        try {
            postMessageService.addFavorite(id,aid,uid,time);
            msg.put("code","0");
            msg.put("message","点赞成功");
        } catch (NullPointerException n){
            n.printStackTrace();
            msg.put("code","-1");
            msg.put("message","点赞失败");
        } finally {
            return msg;
        }
    }

    @ApiOperation(value = "根据id删除点赞记录", notes = "根据id删除点赞记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "文章id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "点赞id", required = true, dataType = "String")
    })
    @RequestMapping(value = "/deleteLikeById", method = RequestMethod.DELETE)
    public JSONObject deleteLikeById(
            @PathVariable String aid,
            @PathVariable String id
    ){
        JSONObject msg = new JSONObject();
        postMessageService.deleteLikeById(aid, id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }

    @ApiOperation(value = "根据id删除收藏记录", notes = "根据id删除收藏记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "文章id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "收藏id", required = true, dataType = "String")
    })
    @RequestMapping(value = "/deleteFavoriteById", method = RequestMethod.DELETE)
    public JSONObject deleteFavoriteById(
            @PathVariable String aid,
            @PathVariable String id
    ){
        JSONObject msg = new JSONObject();
        postMessageService.deleteFavoriteById(aid, id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }


}