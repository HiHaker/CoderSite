package com.ynu.codersite.service;

import com.ynu.codersite.entity.QuestionDTO;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2019/12/8 0008
 * BY Jianlong
 */
@Service
public class AQuestionService {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionTextService questionTextService;

    /**
     * 增加一条问题记录
     * @param questionDTO
     */
    @Transactional
    public void addQuestion(QuestionDTO questionDTO){
        // 存储ES对象
        QuestionText questionText = new QuestionText();
        questionText.setqId(questionDTO.getQid());
        questionText.setTitle(questionDTO.getTitle());
        questionText.setContent(questionDTO.getContent());
        questionText.setLabels(questionDTO.getLabels());
        questionText.setPostTime(questionDTO.getPostTime());
        questionTextService.addItem(questionText);
        // 存储mongoDB对象
        Question question = new Question();
        question.setqId(questionDTO.getQid());
        question.setUserId(questionDTO.getUid());
        question.setImages(questionDTO.getImages());
        question.setPostTime(questionDTO.getPostTime());
        questionService.addQuestion(question);
    }

    /**
     * 删除一条问题记录
     * @param id
     */
    @Transactional
    public void deleteQuestion(String id){
        questionTextService.deleteItem(id);
        questionService.deleteQuestionById(id);
    }

    /**
     * 是否存在问题
     * @param id
     * @return
     */
    public boolean questionIsExist(String id){
        if (questionService.getQuestionById(id) == null){
            return false;
        } else{
            return true;
        }
    }

}