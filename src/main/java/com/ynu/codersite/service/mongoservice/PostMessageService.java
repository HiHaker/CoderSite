package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.repository.mongorepository.PostMessageRepository;
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
public class PostMessageService {
    @Autowired
    PostMessageRepository postMessageRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 增加一条帖子
     * @param postMessage
     */
    public void addPostMessage(PostMessage postMessage){
        postMessageRepository.save(postMessage);
    }

    /**
     * 增加一条点赞记录
     * @param id
     * @param aid
     * @param uid
     * @param time
     */
    public void addLike(String aid, String id, String uid, String time){
        PostMessage postMessage = this.getPostMessageById(aid);
        RelationNode relationNode = new RelationNode(id, uid, time);
        if (postMessage.getLikes() == null){
            List<RelationNode> likesList = new ArrayList<>();
            likesList.add(relationNode);
            postMessage.setLikes(likesList);
        } else {
            postMessage.getLikes().add(relationNode);
        }
        this.updatePostMessage(postMessage);
    }

    /**
     * 增加一条收藏记录
     * @param id
     * @param aid
     * @param uid
     * @param time
     */
    public void addFavorite(String aid, String id, String uid, String time){
        PostMessage postMessage = this.getPostMessageById(aid);
        RelationNode relationNode = new RelationNode(id, uid, time);
        if (postMessage.getFavorites() == null){
            List<RelationNode> favoritesList = new ArrayList<>();
            favoritesList.add(relationNode);
            postMessage.setFavorites(favoritesList);
        } else {
            postMessage.getFavorites().add(relationNode);
        }
        this.updatePostMessage(postMessage);
    }

    /**
     * 根据id删除一个帖子
     * @param id
     */
    public void deletePostMessageById(String id){
        postMessageRepository.deleteById(id);
    }

    /**
     * 根据用户id删除一个帖子
     * @param uid
     */
    public void deletePostMessageByUid(String uid){
        postMessageRepository.deleteByUserId(uid);
    }

    /**
     * 根据id删除一条点赞记录
     * @param aid
     * @param id
     */
    public void deleteLikeById(String aid, String id){
        Query query = new Query(Criteria.where("pId").is(aid));
        Update update = new Update();
        update.pull("likes",Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(query,update,PostMessage.class);
    }

    /**
     * 根据用户id删除其点赞记录
     * @param aid
     * @param uid
     */
    public void deleteLikeByUid(String aid, String uid){
        Query query = new Query(Criteria.where("pId").is(aid));
        Update update = new Update();
        update.pull("likes",Query.query(Criteria.where("userId").is(uid)));
        mongoTemplate.updateMulti(query,update,PostMessage.class);
    }

    /**
     * 根据id删除一条收藏记录
     * @param aid
     * @param id
     */
    public void deleteFavoriteById(String aid, String id){
        Query query = new Query(Criteria.where("pId").is(aid));
        Update update = new Update();
        update.pull("favorites",Query.query(Criteria.where("_id").is(id)));
        mongoTemplate.updateMulti(query,update,PostMessage.class);
    }

    /**
     * 根据用户id删除其收藏记录
     * @param aid
     * @param uid
     */
    public void deleteFavoriteByUid(String aid, String uid){
        Query query = new Query(Criteria.where("pId").is(aid));
        Update update = new Update();
        update.pull("favorites",Query.query(Criteria.where("userId").is(uid)));
        mongoTemplate.updateMulti(query,update,PostMessage.class);
    }

    /**
     * 更新帖子
     * @param postMessage
     */
    public void updatePostMessage(PostMessage postMessage){
        postMessageRepository.save(postMessage);
    }

    /**
     * 获取全部帖子
     * @return
     */
    public List<PostMessage> getAllPostMessage(){
        return postMessageRepository.findAll();
    }

    /**
     * 根据id查询一个帖子
     * @param id
     */
    public PostMessage getPostMessageById(String id){
        return postMessageRepository.findById(id).orElse(null);
    }

    /**
     * 根据用户id获取其发表的所有帖子
     * @param userId
     * @return
     */
    public List<PostMessage> getByUserId(String userId){
        return postMessageRepository.findByUserId(userId);
    }

    /**
     * 分页查询某用户发表的最新的10条文章
     * @param page
     * @return
     */
    public List<PostMessage> getUserNewestPM(String userId, Integer page){
        Page<PostMessage> pageResult = postMessageRepository.findByUserIdOrderByPostTimeDesc(userId, PageRequest.of(page,10));
        return pageResult.getContent();
    }
}