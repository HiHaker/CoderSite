package com.ynu.codersite.service;

import com.ynu.codersite.entity.PostMessage;
import com.ynu.codersite.repository.PostMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
public class PostMessageService {
    @Autowired
    PostMessageRepository postMessageRepository;

    /**
     * 增加一条帖子
     * @param postMessage
     */
    public void addPostMessage(PostMessage postMessage){
        postMessageRepository.save(postMessage);
    }

    /**
     * 根据id删除一个帖子
     * @param pId
     */
    public void deletePostMessageById(String pId){
        postMessageRepository.deletePostMessageByPId(pId);
    }

    /**
     * 更新帖子
     * @param postMessage
     */
    public void updatePostMessage(PostMessage postMessage){
        postMessageRepository.save(postMessage);
    }

    /**
     * 根据id删除一个帖子
     * @param pId
     */
    public PostMessage getPostMessageById(String pId){
        return postMessageRepository.findPostMessageByPId(pId);
    }
}
