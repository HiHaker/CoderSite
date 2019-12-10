package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.repository.mongorepository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    MongoTemplate mongoTemplate;

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
        questionRepository.deleteById(qId);
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
        return questionRepository.findById(qId).orElse(null);
    }

    /**
     * 增加一条点赞记录
     * @param id
     * @param qid
     * @param uid
     * @param time
     */
    public void addLike(String qid, String id, String uid, String time){
        Question question = this.getQuestionById(qid);
        RelationNode relationNode = new RelationNode(id, uid, time);
        if (question.getLikes() == null){
            List<RelationNode> likesList = new ArrayList<>();
            likesList.add(relationNode);
            question.setLikes(likesList);
        } else {
            question.getLikes().add(relationNode);
        }
        questionRepository.save(question);
    }

    /**
     * 增加一条收藏记录
     * @param qid
     * @param id
     * @param uid
     * @param time
     */
    public void addFavorite(String qid, String id, String uid, String time){
        Question question = this.getQuestionById(qid);
        RelationNode relationNode = new RelationNode(id, uid, time);
        if (question.getFavorites() == null){
            List<RelationNode> favoritesList = new ArrayList<>();
            favoritesList.add(relationNode);
            question.setFavorites(favoritesList);
        } else {
            question.getFavorites().add(relationNode);
        }
        questionRepository.save(question);
    }

    /**
     * 根据id删除点赞记录
     * @param qid
     * @param id
     */
    public void deleteLikeById(String qid, String id){
        Query query = new Query(Criteria.where("qId").is(qid));
        Update update = new Update();
        update.pull("likes",Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(query,update,Question.class);
    }

    /**
     * 根据id删除收藏记录
     * @param qid
     * @param id
     */
    public void deleteFavoriteById(String qid, String id){
        Query query = new Query(Criteria.where("qId").is(qid));
        Update update = new Update();
        update.pull("favorites",Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(query,update,Question.class);
    }

    /**
     * 根据用户id获取其发表的所有问题
     * @param userId
     * @return
     */
    public List<Question> getByUserId(String userId){
        return questionRepository.findByUserId(userId);
    }

    /**
     * 分页查询某用户发表的最新的10条问题
     * @param userId
     * @param page
     * @return
     */
    public List<Question> getUserNewestQuestion(String userId, Integer page){
        Page<Question> pageResult = questionRepository.findByUserIdOrderByPostTimeDesc(userId, PageRequest.of(page,10));
        return pageResult.getContent();
    }
}