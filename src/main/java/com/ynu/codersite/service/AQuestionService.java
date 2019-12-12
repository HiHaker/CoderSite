package com.ynu.codersite.service;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.QuestionDTO;
import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.esservice.UserInfoService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import com.ynu.codersite.service.mongoservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserService userService;

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

    /**
     * 封装json对象
     * @param qt
     * @param q
     * @return
     */
    public JSONObject encapsulateJson(Question q, QuestionText qt){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qid",qt.getqId());
        jsonObject.put("userId",q.getUserId());
        jsonObject.put("userNickname",userInfoService.getUserById(q.getUserId()).getNickname());
        jsonObject.put("postTime",q.getPostTime());
        jsonObject.put("labels",qt.getLabels());
        jsonObject.put("title",qt.getTitle());
        jsonObject.put("content",qt.getContent());

        List<RelationNode> likes = q.getLikes();
        if (likes == null){
            jsonObject.put("likeCount",0);
        } else{
            jsonObject.put("likeCount",likes.size());
        }

        List<RelationNode> favorites = q.getLikes();
        if (favorites == null){
            jsonObject.put("collectCount",0);
        } else{
            jsonObject.put("collectCount",favorites.size());
        }

        List<CommentNode> answers = qt.getAnswers();
        if (answers == null){
            jsonObject.put("answerCount",0);
        } else{
            jsonObject.put("answerCount",answers.size());
        }

        return jsonObject;
    }

    /**
     * 获得DTO对象
     * @param qt
     * @param q
     * @return
     */
    public QuestionDTO getDTO(QuestionText qt, Question q){
        QuestionDTO qDTO = new QuestionDTO();
        qDTO.setQid(q.getqId());
        qDTO.setUid(q.getUserId());
        qDTO.setTitle(qt.getTitle());
        qDTO.setLabels(qt.getLabels());
        qDTO.setPostTime(qt.getPostTime());
        qDTO.setContent(qt.getContent());
        qDTO.setImages(q.getImages());

        return qDTO;
    }

    /**
     * 根据问题id获取问题
     * @param qid
     * @return
     */
    public JSONObject getQuestionById(String qid){
        QuestionText qt = questionTextService.getById(qid);
        Question q = questionService.getQuestionById(qid);
        return encapsulateJson(q, qt);
    }

    /**
     * 获得全部问题
     * @return
     */
    public List<QuestionDTO> getAllQuestion(){
        List<QuestionText> questionTexts = questionTextService.getAllQuestionText();
        List<Question> questions = questionService.getAllQuestion();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (int i=0; i<questions.size(); i++){
            QuestionText qt = questionTexts.get(i);
            Question q = questions.get(i);
            questionDTOS.add(getDTO(qt, q));
        }

        return questionDTOS;
    }

    /**
     * 获取最新的10个问题
     * @param page
     * @return
     */
    public List<JSONObject> getNewestQuestion(Integer page){
        List<QuestionText> questionTexts = questionTextService.getNewestQuestion(page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (QuestionText qt:questionTexts){
            Question q = questionService.getQuestionById(qt.getqId());
            jsonObjectList.add(encapsulateJson(q,qt));
        }
        return jsonObjectList;
    }

    /**
     * 根据关键词获取最新问题（标题、内容）
     * @param keyword
     * @param page
     * @return
     */
    public List<JSONObject> getNewestQuestionByKeyword(String keyword, Integer page){
        List<QuestionText> questionTexts = questionTextService.getNewestQuestionByKeyword(keyword, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (QuestionText qt:questionTexts){
            Question q = questionService.getQuestionById(qt.getqId());
            jsonObjectList.add(encapsulateJson(q,qt));
        }
        return jsonObjectList;
    }

    /**
     * 根据关键词获取最新问题（标签）
     * @param keyword
     * @param page
     * @return
     */
    public List<JSONObject> getNewestQuestionByLabel(String keyword, Integer page){
        List<QuestionText> questionTexts = questionTextService.getNewestQuestionByLabel(keyword, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (QuestionText qt:questionTexts){
            Question q = questionService.getQuestionById(qt.getqId());
            jsonObjectList.add(encapsulateJson(q,qt));
        }
        return jsonObjectList;
    }

    /**
     * 获取某用户发表的最新10条问题
     * @param userId
     * @param page
     * @return
     */
    public List<JSONObject> getUserNewestQuestion(String userId, Integer page){
        List<Question> questions = questionService.getUserNewestQuestion(userId, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (Question q:questions){
            QuestionText qt = questionTextService.getById(q.getqId());
            jsonObjectList.add(encapsulateJson(q, qt));
        }
        return jsonObjectList;
    }

    /**
     * 返回关注的人发表的最新的问题
     * @param userId
     * @return
     */
    public List<JSONObject> getFollowsNewestQuestion(String userId){
        List<Question> result = new ArrayList<>();
        List<String> follows = userService.getAllFollows(userId);

        if (follows == null || follows.size()==0){
            return null;
        }

        for (String uid:follows){
            result.addAll(questionService.getByUserId(uid));
        }

        Collections.sort(result, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o1.getPostTime().compareTo(o2.getPostTime());
            }
        });

        List<JSONObject> jsonResult = new ArrayList<>();
        for (int i=result.size()-1; i>=0; i--){
            JSONObject jsonObject = new JSONObject();
            Question q = result.get(i);
            QuestionText qt = questionTextService.getById(q.getqId());
            jsonResult.add(encapsulateJson(q,qt));
        }

        return jsonResult;
    }

}