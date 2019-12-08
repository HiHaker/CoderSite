package com.ynu.codersite.service;

import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addPostMessage(PostMessageDTO postMessageDTO){
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

    /**
     * 判断文章是否存在
     * @param aid
     * @return
     */
    public boolean postMessageIsExist(String aid){
        if (pmService.getPostMessageById(aid) == null){
            return false;
        } else{
            return true;
        }
    }
}