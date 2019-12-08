package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.service.APostMessageService;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import io.swagger.annotations.ApiImplicitParam;
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
    @Autowired
    PostMessageTextService postMessageTextService;

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

    /**
     * 删除文章
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除文章", notes = "根据id删除文章")
    @RequestMapping(value = "/deleteArticleById", method = RequestMethod.DELETE)
    public JSONObject deleteArticleById(
            @RequestParam String id
    ){
        JSONObject msg = new JSONObject();
        aPostMessageService.deleteById(id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }

    /**
     * 增加点赞
     * @param aid
     * @param id
     * @param uid
     * @param time
     * @return
     */
    @ApiOperation(value = "增加点赞", notes = "增加点赞")
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public JSONObject addLike(
            @RequestParam String aid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aPostMessageService.postMessageIsExist(aid)){
            postMessageService.addLike(aid,id,uid,time);
            msg.put("code","0");
            msg.put("message","点赞成功");
        } else{
            msg.put("code","-1");
            msg.put("message","点赞失败, 文章不存在");
        }
        return msg;
    }

    @ApiOperation(value = "增加收藏", notes = "增加收藏")
    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    public JSONObject addFavorite(
            @RequestParam String aid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aPostMessageService.postMessageIsExist(aid)){
            postMessageService.addFavorite(aid,id,uid,time);
            msg.put("code","0");
            msg.put("message","收藏成功");
        } else{
            msg.put("code","-1");
            msg.put("message","收藏失败, 文章不存在");
        }
        return msg;
    }

    /**
     * 增加评论
     * @param aid
     * @param id
     * @param uid
     * @param content
     * @param time
     * @return
     */
    @ApiOperation(value = "增加评论", notes = "增加评论")
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public JSONObject addComment(
            @RequestParam String aid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String content,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (postMessageService.getPostMessageById(aid) == null){
            msg.put("code",-1);
            msg.put("message","评论失败,没有此文章");
        } else {
            postMessageTextService.addComment(aid, id, uid, content, time);
            msg.put("code",0);
            msg.put("message","评论成功");
        }
        return msg;
    }

    /**
     * 删除评论
     * @param aid
     * @param id
     * @return
     */
    @ApiOperation(value = "删除评论", notes = "删除评论")
    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public JSONObject deleteComment(
            @RequestParam String aid,
            @RequestParam String id
    ){
        JSONObject msg = new JSONObject();
        if (postMessageService.getPostMessageById(aid) == null){
            msg.put("code",-1);
            msg.put("message","删除评论失败,没有此文章");
        } else {
            postMessageTextService.deleteComment(aid, id);
            msg.put("code",0);
            msg.put("message","删除评论成功");
        }
        return msg;
    }

    /**
     * 删除点赞
     * @param aid
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除点赞记录", notes = "根据id删除点赞记录")
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

    /**
     * 删除收藏
     * @param aid
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除收藏记录", notes = "根据id删除收藏记录")
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