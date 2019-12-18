package com.ynu.codersite.service;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.MessageNode;
import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.entity.mogoentity.TextNode;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.esservice.UserInfoService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import com.ynu.codersite.service.mongoservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/18 0018
 * BY Jianlong
 */
@Service
public class MessageService {
    @Autowired
    PostMessageService postMessageService;
    @Autowired
    PostMessageTextService postMessageTextService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionTextService questionTextService;
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;

    // 封装点赞收藏消息节点(帖子)
    public MessageNode encapsulateMN(RelationNode rn, PostMessageText pmt, String uid, String type){
        MessageNode mn = new MessageNode();
        String iuid = rn.getUserId();
        mn.setUid(iuid);
        mn.setAvatar(userService.getUserById(iuid).getAvatarId());
        mn.setNickname(userInfoService.getUserById(iuid).getNickname());
        mn.setTime(rn.getTime());
        mn.setAttent(userService.isFollow(uid, iuid));
        mn.setPostType("1");
        TextNode tn = new TextNode();
        tn.setId(pmt.getpId());
        tn.setPara(pmt.getContent().get(0).getPara());
        tn.setImage(pmt.getContent().get(0).getImage());
        mn.setPost(tn);
        mn.setMessageType(type);
        return mn;
    }

    // 封装点赞收藏消息节点(问题)
    public MessageNode encapsulateMN(RelationNode rn, QuestionText qt, String uid, String type){
        MessageNode mn = new MessageNode();
        String iuid = rn.getUserId();
        mn.setUid(iuid);
        mn.setAvatar(userService.getUserById(iuid).getAvatarId());
        mn.setNickname(userInfoService.getUserById(iuid).getNickname());
        mn.setTime(rn.getTime());
        mn.setAttent(userService.isFollow(uid, iuid));
        mn.setPostType("2");
        TextNode tn = new TextNode();
        tn.setId(qt.getqId());
        tn.setPara(qt.getContent());
        List<String> images = questionService.getQuestionById(qt.getqId()).getImages();
        if (images.size() == 0){
            tn.setImage("");
        } else {
            tn.setImage(images.get(0));
        }
        mn.setPost(tn);
        mn.setMessageType(type);
        return mn;
    }

    // 封装评论消息节点(帖子)
    public MessageNode encapsulateMN(CommentNode cn, PostMessageText pmt, String uid){
        MessageNode mn = new MessageNode();
        String iuid = cn.getUserId();
        mn.setUid(iuid);
        mn.setAvatar(userService.getUserById(iuid).getAvatarId());
        mn.setNickname(userInfoService.getUserById(iuid).getNickname());
        mn.setTime(cn.getTime());
        mn.setAttent(userService.isFollow(uid, iuid));
        mn.setPostType("3");
        TextNode tn = new TextNode();
        tn.setId(pmt.getpId());
        tn.setPara(pmt.getContent().get(0).getPara());
        tn.setImage(pmt.getContent().get(0).getImage());
        mn.setPost(tn);
        mn.setComment(cn.getContent());
        return mn;
    }

    // 封装评论消息节点(问题)
    public MessageNode encapsulateMN(CommentNode cn, QuestionText qt, String uid){
        MessageNode mn = new MessageNode();
        String iuid = cn.getUserId();
        mn.setUid(iuid);
        mn.setAvatar(userService.getUserById(iuid).getAvatarId());
        mn.setNickname(userInfoService.getUserById(iuid).getNickname());
        mn.setTime(cn.getTime());
        mn.setAttent(userService.isFollow(uid, iuid));
        mn.setPostType("4");
        TextNode tn = new TextNode();
        tn.setId(qt.getqId());
        tn.setPara(qt.getContent());
        List<String> images = questionService.getQuestionById(qt.getqId()).getImages();
        if (images.size() == 0){
            tn.setImage("");
        } else {
            tn.setImage(images.get(0));
        }
        mn.setPost(tn);
        mn.setAnswer(cn.getContent());
        return mn;
    }

    // 增加点赞和收藏的消息(帖子)
    public List<MessageNode> addRelationMessage(List<MessageNode> messages, PostMessage pm,
                                                String uid, String type, String time){
        List<RelationNode> rList;
        if (type.equals("1")){
            rList = pm.getLikes();
        } else{
            rList = pm.getFavorites();
        }
        for (RelationNode rn:rList){
            // 如果发表时间在给定时间之后，说明是新消息
            if (rn.getTime().compareTo(time) > 0){
                PostMessageText pmt = postMessageTextService.getById(pm.getpId());
                messages.add(this.encapsulateMN(rn,pmt,uid,type));
            }
        }
        return messages;
    }

    // 增加点赞和收藏的消息(问题)
    public List<MessageNode> addRelationMessage(List<MessageNode> messages, Question q,
                                                String uid, String type, String time){
        List<RelationNode> rList;
        if (type.equals("1")){
            rList = q.getLikes();
        } else{
            rList = q.getFavorites();
        }
        for (RelationNode rn:rList){
            // 如果发表时间在给定时间之后，说明是新消息
            if (rn.getTime().compareTo(time) > 0){
                QuestionText qt = questionTextService.getById(q.getqId());
                messages.add(this.encapsulateMN(rn,qt,uid,type));
            }
        }
        return messages;
    }

    // 增加评论的消息
    public List<MessageNode> addCommentMessage(List<MessageNode> messages, PostMessageText pmt,
                                               String uid, String time){
        List<CommentNode> comments = pmt.getComments();
        if (comments == null){
            return messages;
        } else{
            for (CommentNode cn:comments){
                if (cn.getTime().compareTo(time) > 0){
                    messages.add(this.encapsulateMN(cn,pmt,uid));
                }
            }
        }

        return messages;
    }

    // 增加评论的消息
    public List<MessageNode> addCommentMessage(List<MessageNode> messages, QuestionText qt,
                                               String uid, String time){
        List<CommentNode> answers = qt.getAnswers();
        if (answers == null){
            return messages;
        } else{
            for (CommentNode cn:answers){
                if (cn.getTime().compareTo(time) > 0){
                    messages.add(this.encapsulateMN(cn,qt,uid));
                }
            }
        }

        return messages;
    }

    // 获取最新消息
    public List<MessageNode> getNewMessages( String uid, String time){
        // 新消息列表 1 点赞 2 收藏 3 评论 4 回答
        List<MessageNode> messages = new ArrayList<>();
        // 获取用户发表的所有帖子
        List<PostMessage> myPosts = postMessageService.getByUserId(uid);
        for (PostMessage pm:myPosts){
            // 获取点赞和收藏的消息
            messages = this.addRelationMessage(messages, pm, uid, "1", time);
            messages = this.addRelationMessage(messages, pm, uid, "2", time);

            // 获取评论的消息
            PostMessageText pmt = postMessageTextService.getById(pm.getpId());
            messages = this.addCommentMessage(messages, pmt, uid, time);
        }

        // 获取用户发表的所有问题
        List<Question> myQuestions = questionService.getByUserId(uid);
        for (Question q:myQuestions){
            // 获取点赞和收藏的消息
            messages = this.addRelationMessage(messages, q, uid,"1", time);
            messages = this.addRelationMessage(messages, q, uid,"2", time);

            // 获取回答的消息
            QuestionText qt = questionTextService.getById(q.getqId());
            messages = this.addCommentMessage(messages, qt, uid, time);
        }

        return messages;
    }
}
