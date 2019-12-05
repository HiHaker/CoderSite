package com.ynu.codersite.service;

import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@Service
public class APostMessageService {
    // ES数据库服务
    @Autowired
    PostMessageTextService pmtService;
    // MongoDB数据库服务
    @Autowired
    PostMessageService pmService;

    // 发表一个帖子(文章)
    @Transactional
    public void postPostMessage(PostMessageDTO postMessageDTO){
        // ES对象
        PostMessageText postMessageText = new PostMessageText();
        postMessageText.setpId(postMessageDTO.getAid());
        postMessageText.setTitle(postMessageDTO.getTitle());
        postMessageText.setContent(postMessageDTO.getContent());
        postMessageText.setLabels(postMessageDTO.getLabels());
        pmtService.addItem(postMessageText);
        // mongoDB对象
        PostMessage postMessage = new PostMessage();
        postMessage.setpId(postMessageDTO.getAid());
        postMessage.setUserId(postMessageDTO.getUid());
        postMessage.setPostTime(postMessageDTO.getPostTime());
        pmService.addPostMessage(postMessage);
    }

    // 根据id删除文章对象
    @Transactional
    public void deleteById(String id){
        pmtService.deleteItem(id);
        pmService.deletePostMessageById(id);
    }

    // 增加一条点赞记录
    public void addLikes(String id, String aid, String uid, String time){
        pmService.addLikes(id, aid, uid, time);
    }

    // 删除一条点赞记录
    public void deleteLikesBy(String aid, String id){

    }
}