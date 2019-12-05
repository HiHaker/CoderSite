package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.repository.mongorepository.PostMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public void addLikes(String id, String aid, String uid, String time){
        PostMessage postMessage = this.getPostMessageById(aid);
        RelationNode relationNode = new RelationNode(id, uid, time);
        if (postMessage.getLikes() == null){
            List<RelationNode> likeList = new ArrayList<>();
            likeList.add(relationNode);
            postMessage.setLikes(likeList);
        } else {
            postMessage.getLikes().add(relationNode);
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

    public void deleteLikesById(String id){

    }

    /**
     * 更新帖子
     * @param postMessage
     */
    public void updatePostMessage(PostMessage postMessage){
        postMessageRepository.save(postMessage);
    }

    /**
     * 根据id查询一个帖子
     * @param id
     */
    public PostMessage getPostMessageById(String id){
        return postMessageRepository.findById(id).orElse(null);
    }
}