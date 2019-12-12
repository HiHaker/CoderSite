package com.ynu.codersite.service;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.entity.RelationNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.esservice.UserInfoService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import com.ynu.codersite.service.mongoservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserService userService;

    // 发表一个帖子(文章)
    @Transactional
    public void addPostMessage(PostMessageDTO postMessageDTO){
        // ES对象
        PostMessageText postMessageText = new PostMessageText();
        postMessageText.setpId(postMessageDTO.getAid());
        postMessageText.setTitle(postMessageDTO.getTitle());
        postMessageText.setContent(postMessageDTO.getContent());
        postMessageText.setLabels(postMessageDTO.getLabels());
        postMessageText.setPostTime(postMessageDTO.getPostTime());
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

    /**
     * 封装json对象
     * @param pm
     * @param pmt
     * @return
     */
    public JSONObject encapsulateJson(PostMessage pm, PostMessageText pmt){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aid",pmt.getpId());
        jsonObject.put("userId",pm.getUserId());
        jsonObject.put("userNickname",userInfoService.getUserById(pm.getUserId()).getNickname());
        jsonObject.put("postTime",pm.getPostTime());
        jsonObject.put("labels",pmt.getLabels());
        jsonObject.put("title",pmt.getTitle());
        jsonObject.put("content",pmt.getContent().get(0));

        List<RelationNode> likes = pm.getLikes();
        if (likes == null){
            jsonObject.put("likeCount",0);
        } else{
            jsonObject.put("likeCount",likes.size());
        }

        List<RelationNode> favorites = pm.getLikes();
        if (favorites == null){
            jsonObject.put("collectCount",0);
        } else{
            jsonObject.put("collectCount",favorites.size());
        }

        List<CommentNode> comments = pmt.getComments();
        if (comments == null){
            jsonObject.put("commentCount",0);
        } else{
            jsonObject.put("commentCount",comments.size());
        }

        return jsonObject;
    }

    /**
     * 返回最新的10条文章
     * @param page
     * @return
     */
    public List<JSONObject> getNewestPostMessage(Integer page){
        List<PostMessageText> postMessageTexts = pmtService.getNewestPostMessage(page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (PostMessageText pmt:postMessageTexts){
            PostMessage pm = pmService.getPostMessageById(pmt.getpId());
            jsonObjectList.add(encapsulateJson(pm,pmt));
        }
        return jsonObjectList;
    }

    /**
     * 根据关键词查询最新的文章（标题和内容）
     * @param page
     * @return
     */
    public List<JSONObject> getNewestPostMessageByKeyword(String keyword, Integer page){
        List<PostMessageText> postMessageTexts = pmtService.getNewestPostMessageByKeyword(keyword, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (PostMessageText pmt:postMessageTexts){
            PostMessage pm = pmService.getPostMessageById(pmt.getpId());
            jsonObjectList.add(encapsulateJson(pm,pmt));
        }
        return jsonObjectList;
    }

    /**
     * 根据关键词查询最新的文章(标签)
     * @param page
     * @return
     */
    public List<JSONObject> getNewestPMByLabel(String keyword, Integer page){
        List<PostMessageText> postMessageTexts = pmtService.getNewestPMByLabel(keyword, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (PostMessageText pmt:postMessageTexts){
            PostMessage pm = pmService.getPostMessageById(pmt.getpId());
            jsonObjectList.add(encapsulateJson(pm,pmt));
        }
        return jsonObjectList;
    }

    /**
     * 获取某用户发表的最新10条文章
     * @param userId
     * @param page
     * @return
     */
    public List<JSONObject> getUserNewestPM(String userId, Integer page){
        List<PostMessage> postMessages = pmService.getUserNewestPM(userId, page);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (PostMessage pm:postMessages){
            PostMessageText pmt = pmtService.getById(pm.getpId());
            jsonObjectList.add(encapsulateJson(pm,pmt));
        }
        return jsonObjectList;
    }

    /**
     * DTO对象转换
     * @param pmt
     * @param pm
     * @return
     */
    public PostMessageDTO getDTO(PostMessageText pmt, PostMessage pm){
        PostMessageDTO pmDTO = new PostMessageDTO();
        pmDTO.setAid(pm.getpId());
        pmDTO.setUid(pm.getUserId());
        pmDTO.setContent(pmt.getContent());
        pmDTO.setLabels(pmt.getLabels());
        pmDTO.setPostTime(pmt.getPostTime());
        return pmDTO;
    }

    /**
     * 根据帖子id获得帖子
     * @param aid
     * @return
     */
    public JSONObject getPostMessageById(String aid){
        PostMessageText pmt = pmtService.getById(aid);
        PostMessage pm = pmService.getPostMessageById(aid);
        return encapsulateJson(pm, pmt);
    }

    /**
     * 获取全部帖子
     * @return
     */
    public List<PostMessageDTO> getAllPostMessage(){
        List<PostMessageText> postMessageTexts = pmtService.getAllPostMessageText();
        List<PostMessage> postMessages = pmService.getAllPostMessage();
        List<PostMessageDTO> pmDTOS = new ArrayList<>();

        for (int i=0; i<postMessages.size(); i++){
            PostMessageText pmt = postMessageTexts.get(i);
            PostMessage pm = postMessages.get(i);
            pmDTOS.add(getDTO(pmt,pm));
        }

        return pmDTOS;
    }

    /**
     * 返回关注的人发表的最新的文章
     * @param userId
     * @return
     */
    public List<JSONObject> getFollowsNewestPM(String userId){
        List<PostMessage> result = new ArrayList<>();
        List<String> follows = userService.getAllFollows(userId);

        if (follows == null || follows.size() == 0){
            return null;
        }

        for (String uid:follows){
            result.addAll(pmService.getByUserId(uid));
        }

        Collections.sort(result, new Comparator<PostMessage>() {
            @Override
            public int compare(PostMessage o1, PostMessage o2) {
                return o1.getPostTime().compareTo(o2.getPostTime());
            }
        });

        List<JSONObject> jsonResult = new ArrayList<>();
        for (int i=result.size()-1; i>=0; i--){
            JSONObject jsonObject = new JSONObject();
            PostMessage pm = result.get(i);
            PostMessageText pmt = pmtService.getById(pm.getpId());
            jsonResult.add(encapsulateJson(pm,pmt));
        }

        return jsonResult;
    }
}