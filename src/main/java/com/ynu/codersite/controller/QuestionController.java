package com.ynu.codersite.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.QuestionDTO;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionTextService questionTextService;
    @Autowired
    QuestionService questionService;

    JSONObject jsonObject;

    @ApiOperation(value = "新增问题", notes = "新增问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qid", value = "问题id", required = true),
            @ApiImplicitParam(name = "uid", value = "用户id", required = true),
            @ApiImplicitParam(name = "postTime", value = "发表时间", required = true),
            @ApiImplicitParam(name = "labels", value = "标签", allowMultiple = true),
            @ApiImplicitParam(name = "title", value = "标题", required = true),
            @ApiImplicitParam(name = "content", value = "问题内容", required = true),
            @ApiImplicitParam(name = "images", value = "图像", allowMultiple = true),
    })
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public JSONObject addQuestion(
            @RequestBody QuestionDTO questionDTO
    ){
        jsonObject = new JSONObject();
        Question question = new Question();
        QuestionText questionText = new QuestionText();

        return null;
    }
}