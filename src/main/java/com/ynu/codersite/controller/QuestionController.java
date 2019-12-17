package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.QuestionDTO;
import com.ynu.codersite.service.AQuestionService;
import com.ynu.codersite.service.AUserService;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionTextService questionTextService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AQuestionService aQuestionService;
    @Autowired
    AUserService aUserService;

    /**
     * 新增一个问题
     * @param questionDTO
     * @return
     */
    @ApiOperation(value = "新增问题", notes = "新增问题")
    @ApiImplicitParam(name = "questionDTO", value = "发表问题参数信息", required = true, dataType = "QuestionDTO")
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public JSONObject addQuestion(
            @RequestBody QuestionDTO questionDTO
    ){
        JSONObject msg = new JSONObject();
        aQuestionService.addQuestion(questionDTO);
        msg.put("code","0");
        msg.put("message","发表成功");
        return msg;
    }

    /**
     * 根据id删除问题
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除问题", notes = "根据id删除问题")
    @RequestMapping(value = "/deleteQuestionById", method = RequestMethod.DELETE)
    public JSONObject deleteArticleById(
            @RequestParam String id
    ){
        JSONObject msg = new JSONObject();
        aQuestionService.deleteQuestion(id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }

    /**
     * 增加点赞
     * @param qid
     * @param id
     * @param uid
     * @param time
     * @return
     */
    @ApiOperation(value = "增加点赞", notes = "增加点赞")
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public JSONObject addLike(
            @RequestParam String qid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aQuestionService.questionIsExist(qid)){
            questionService.addLike(qid,id,uid,time);
            msg.put("code","0");
            msg.put("message","点赞成功");
        } else{
            msg.put("code","-1");
            msg.put("message","点赞失败, 文章不存在");
        }
        return msg;
    }

    /**
     * 增加收藏
     * @param qid
     * @param id
     * @param uid
     * @param time
     * @return
     */
    @ApiOperation(value = "增加收藏", notes = "增加收藏")
    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    public JSONObject addFavorite(
            @RequestParam String qid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aQuestionService.questionIsExist(qid)){
            questionService.addFavorite(qid,id,uid,time);
            msg.put("code","0");
            msg.put("message","收藏成功");
        } else{
            msg.put("code","-1");
            msg.put("message","收藏失败, 文章不存在");
        }
        return msg;
    }

    /**
     * 增加回答
     * @param qid
     * @param id
     * @param uid
     * @param content
     * @param time
     * @return
     */
    @ApiOperation(value = "增加回答", notes = "增加回答")
    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
    public JSONObject addAnswer(
            @RequestParam String qid,
            @RequestParam String id,
            @RequestParam String uid,
            @RequestParam String content,
            @RequestParam String time
    ){
        JSONObject msg = new JSONObject();
        if (aQuestionService.questionIsExist(qid)){
            questionTextService.addAnswer(qid, id, uid, content, time);
            msg.put("code",0);
            msg.put("message","回答成功");
        } else {
            msg.put("code",-1);
            msg.put("message","回答失败,没有此问题");
        }
        return msg;
    }

    /**
     * 删除回答
     * @param qid
     * @param id
     * @return
     */
    @ApiOperation(value = "删除回答", notes = "删除回答")
    @RequestMapping(value = "/deleteAnswer", method = RequestMethod.DELETE)
    public JSONObject deleteAnswer(
            @RequestParam String qid,
            @RequestParam String id
    ){
        JSONObject msg = new JSONObject();
        if (aQuestionService.questionIsExist(qid)){
            msg.put("code",-1);
            msg.put("message","删除回答失败,没有此问题");
        } else {
            questionTextService.deleteAnswer(qid, id);
            msg.put("code",0);
            msg.put("message","删除回答成功");
        }
        return msg;
    }

    /**
     * 删除点赞
     * @param qid
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除点赞记录", notes = "根据id删除点赞记录")
    @RequestMapping(value = "/deleteLikeById", method = RequestMethod.DELETE)
    public JSONObject deleteLikeById(
            @PathVariable String qid,
            @PathVariable String id
    ){
        JSONObject msg = new JSONObject();
        questionService.deleteLikeById(qid, id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }

    /**
     * 删除收藏
     * @param qid
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除收藏记录", notes = "根据id删除收藏记录")
    @RequestMapping(value = "/deleteFavoriteById", method = RequestMethod.DELETE)
    public JSONObject deleteFavoriteById(
            @PathVariable String qid,
            @PathVariable String id
    ){
        JSONObject msg = new JSONObject();
        questionService.deleteFavoriteById(qid, id);
        msg.put("code","0");
        msg.put("message","删除成功");
        return msg;
    }

    /**
     * 根据id获取问题
     * @return
     */
    @ApiOperation(value = "根据id获取问题", notes = "根据id获取问题")
    @RequestMapping(value = "/getQuestionById", method = RequestMethod.GET)
    public JSONObject getQuestionById(
            @RequestParam String qid,
            @RequestParam String uid
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("question",aQuestionService.getQuestionById(uid, qid));
        return msg;
    }

    /**
     * 获取全部问题
     * @return
     */
    @ApiOperation(value = "获取全部问题", notes = "获取全部问题")
    @RequestMapping(value = "/getAllQuestions", method = RequestMethod.GET)
    public JSONObject getAllQuestions(){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("questionList",aQuestionService.getAllQuestion());
        return msg;
    }

    /**
     * 获取最新10条问题
     * @param page
     * @return
     */
    @ApiOperation(value = "获取最新的10条问题", notes = "获取最新的10条问题")
    @RequestMapping(value = "/getNewQuestions", method = RequestMethod.GET)
    public JSONObject getNewQuestions(
            @RequestParam Integer page
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("questionList",aQuestionService.getNewestQuestion(page));
        return msg;
    }

    /**
     * 获取用户发表的最新10条问题
     * @param uid
     * @param page
     * @return
     */
    @ApiOperation(value = "获取用户发表的最新的10条问题", notes = "获取用户发表的最新的10条问题")
    @RequestMapping(value = "/getUserNewestQuestion", method = RequestMethod.GET)
    public JSONObject etUserNewestQuestion(
            @RequestParam String uid,
            @RequestParam Integer page
    ){
        JSONObject msg = new JSONObject();

        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("questionList",aQuestionService.getUserNewestQuestion(uid, page));
        } else{
            msg.put("code","-1");
            msg.put("message","查询失败, 用户不存在");
        }
        return msg;
    }

    /**
     * 获取关注的人发表的问题
     * @param uid
     * @return
     */
    @ApiOperation(value = "获取关注的人发表的问题", notes = "获取关注的人发表的问题")
    @RequestMapping(value = "/getFollowsQuestions", method = RequestMethod.GET)
    public JSONObject getFollowsNewestQuestion(
            @RequestParam String uid
    ){

        JSONObject msg = new JSONObject();

        if (aUserService.userIsExist(uid)){
            msg.put("code",0);
            msg.put("questionList",aQuestionService.getFollowsNewestQuestion(uid));
        } else{
            msg.put("code","-1");
            msg.put("message","查询失败, 用户不存在");
        }
        return msg;
    }

    /**
     * 根据关键词查询最新的问题（标题和内容）
     * @param keyword
     * @param page
     * @return
     */
    @ApiOperation(value = "根据关键词查询最新的问题（标题和内容）", notes = "根据关键词查询最新的问题（标题和内容）")
    @RequestMapping(value = "/getQuestionsByKeywords", method = RequestMethod.GET)
    public JSONObject getNewestQuestionByKeyword(
            @RequestParam String keyword,
            @RequestParam Integer page
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("questionList",aQuestionService.getNewestQuestionByKeyword(keyword, page));
        return msg;
    }

    /**
     * 根据关键词查询最新的问题(标签)
     * @param keyword
     * @param page
     * @return
     */
    @ApiOperation(value = "根据关键词查询最新的问题(标签)", notes = "根据关键词查询最新的问题(标签)")
    @RequestMapping(value = "/getQuestionsByLabels", method = RequestMethod.GET)
    public JSONObject getNewestQuestionByLabel(
            @RequestParam String keyword,
            @RequestParam Integer page
    ){
        JSONObject msg = new JSONObject();
        msg.put("code",0);
        msg.put("questionList",aQuestionService.getNewestQuestionByLabel(keyword, page));
        return msg;
    }
}