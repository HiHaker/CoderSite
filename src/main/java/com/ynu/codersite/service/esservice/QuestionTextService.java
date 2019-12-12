package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * 根据id查询问题文本
     * @param qId
     * @return
     */
    public QuestionText getById(String qId){
        return questionTextRepository.findById(qId).orElse(null);
    }

    /**
     * 获取全部问题文本
     * @return
     */
    public List<QuestionText> getAllQuestionText(){
        return questionTextRepository.findAll();
    }

    /**
     * 获取最新的10个问题
     * @param page
     * @return
     */
    public List<QuestionText> getNewestQuestion(Integer page){
        Page<QuestionText> pageResult = questionTextRepository.findByOrderByPostTimeDesc(PageRequest.of(page,10));
        return pageResult.getContent();
    }

    /**
     * 根据关键词查询最新的问题（标题和内容）
     * @param keyword
     * @param page
     * @return
     */
    public List<QuestionText> getNewestQuestionByKeyword(String keyword, Integer page){
        Page<QuestionText> pageResult = questionTextRepository.findByTitleLikeOrContentLikeOrderByPostTimeDesc(keyword, keyword, PageRequest.of(page,10));
        return pageResult.getContent();
    }

    /**
     * 根据关键词查询最新的问题（标签）
     * @param keyword
     * @param page
     * @return
     */
    public List<QuestionText> getNewestQuestionByLabel(String keyword, Integer page){
        Page<QuestionText> pageResult = questionTextRepository.findByLabelsContainsOrderByPostTimeDesc(keyword, PageRequest.of(page,10));
        return pageResult.getContent();
    }
}
