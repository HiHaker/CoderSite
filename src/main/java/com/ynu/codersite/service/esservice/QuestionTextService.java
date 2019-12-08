package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/2 0002
 * BY Jianlong
 */
@Service
public class QuestionTextService {
    @Autowired
    QuestionTextRepository questionTextRepository;

    /**
     * 增加一条问题文本记录
     * @param questionText
     */
    public void addItem(QuestionText questionText){
        questionTextRepository.save(questionText);
    }

    /**
     * 增加一条回答记录
     * @param qid
     * @param id
     * @param uid
     * @param content
     * @param time
     */
    public void addAnswer(String qid, String id, String uid, String content, String time){
        QuestionText questionText = questionTextRepository.findById(qid).orElse(null);
        CommentNode commentNode = new CommentNode(id,uid,content,time);
        if (questionText.getAnswers() == null){
            List<CommentNode> answers = new ArrayList<>();
            answers.add(commentNode);
            questionText.setAnswers(answers);
        } else{
            questionText.getAnswers().add(commentNode);
        }
        questionTextRepository.save(questionText);
    }

    /**
     * 根据问题文本记录删除对应的项
     * @param id
     */
    public void deleteItem(String id){
        questionTextRepository.deleteById(id);
    }

    /**
     * 删除一条回答记录
     * @param qid
     * @param id
     */
    public void deleteAnswer(String qid, String id){
        QuestionText questionText = questionTextRepository.findById(qid).orElse(null);
        List<CommentNode> answers = questionText.getAnswers();
        for (int i=0; i<answers.size(); i++){
            if (answers.get(i).getId().equals(id)){
                answers.remove(i);
                break;
            }
        }
        questionText.setAnswers(answers);
        questionTextRepository.save(questionText);
    }
}
