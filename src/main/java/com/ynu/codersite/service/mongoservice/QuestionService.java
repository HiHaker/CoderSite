package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.repository.mongorepository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    /**
     * 增加一个问题
     * @param question
     */
    public void addQuestion(Question question){
        questionRepository.save(question);
    }

    /**
     * 删除一个问题
     * @param qId
     */
    public void deleteQuestionById(String qId){
        questionRepository.deleteQuestionByQId(qId);
    }

    /**
     * 更新问题信息
     * @param question
     */
    public void updateQuestion(Question question){
        questionRepository.save(question);
    }

    /**
     * 根据问题的id查询问题
     * @param qId
     * @return
     */
    public Question getQuestionById(String qId){
        return questionRepository.findQuestionByQId(qId);
    }
}
