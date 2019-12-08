package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Service
public class PostMessageTextService {
    @Autowired
    PostMessageTextRepository postMessageTextRepository;

    /**
     * 增加一条文章文本记录
     * @param postMessageText
     */
    public void addItem(PostMessageText postMessageText){
        postMessageTextRepository.save(postMessageText);
    }

    /**
     * 根据文章文本记录删除对应的项
     * @param id
     */
    public void deleteItem(String id){
        postMessageTextRepository.deleteById(id);
    }

    /**
     * 增加一条评论记录
     * @param aid
     * @param id
     * @param uid
     * @param content
     * @param time
     */
    public void addComment(String aid, String id, String uid, String content, String time){
        PostMessageText postMessageText = postMessageTextRepository.findById(aid).orElse(null);
        CommentNode commentNode = new CommentNode(id,uid,content,time);
        if (postMessageText.getComments() == null){
            List<CommentNode> comments = new ArrayList<>();
            comments.add(commentNode);
            postMessageText.setComments(comments);
        } else{
            postMessageText.getComments().add(commentNode);
        }
        postMessageTextRepository.save(postMessageText);
    }

    /**
     * 删除一条评论记录
     * @param aid
     * @param id
     */
    public void deleteComment(String aid, String id){
        PostMessageText postMessageText = postMessageTextRepository.findById(aid).orElse(null);
        List<CommentNode> comments = postMessageText.getComments();
        for (int i=0; i<comments.size(); i++){
            if (comments.get(i).getId().equals(id)){
                comments.remove(i);
                break;
            }
        }
        postMessageText.setComments(comments);
        postMessageTextRepository.save(postMessageText);
    }
}
