package com.ynu.codersite.service;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.UserDTO;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.entity.mogoentity.Question;
import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.esservice.UserInfoService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import com.ynu.codersite.service.mongoservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/8 0008
 * BY Jianlong
 */
@Service
public class AUserService {

    // Mongodb数据库
    @Autowired
    UserService userService;
    // ES数据库
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PostMessageService postMessageService;
    @Autowired
    PostMessageTextService postMessageTextService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionTextService questionTextService;

    /**
     * 增加一个用户
     * @param userDTO
     */
    @Transactional
    public void addUser(UserDTO userDTO){
        // ES对象
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDTO.getUserId());
        userInfo.setNickname(userDTO.getNickname());
        userInfo.setSignature(userDTO.getSignature());
        userInfo.setLabels(userDTO.getLabels());
        userInfoService.addItem(userInfo);

        // mongoDB对象
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getPassword());
        user.setAvatarId(userDTO.getAvatarId());
        user.setCoverPicture(userDTO.getCoverPicture());
        user.setSex(userDTO.isSex());
        user.setBirthday(userDTO.getBirthday());
        user.setMailbox(userDTO.getMailbox());
        user.setRegisterDate(userDTO.getRegisterDate());
        userService.addUser(user);
    }

    /**
     * 根据id删除一个用户对象
     * @param id
     */
    @Transactional
    public void deleteUser(String id){
        // 删除ES对象
        userInfoService.deleteItem(id);
        // 删除mongo对象
        userService.deleteUserById(id);
        // 删除用户发表的帖子ES
        List<PostMessage> postMessages = postMessageService.getByUserId(id);
        for (PostMessage p:postMessages){
            postMessageTextService.deleteItem(p.getpId());
        }
        // 删除用户发表的帖子Mongo
        postMessageService.deletePostMessageByUid(id);

        // 删除用户发表的问题ES
        List<Question> questions = questionService.getByUserId(id);
        for (Question q:questions){
            questionTextService.deleteItem(q.getqId());
        }
        // 删除用户发表的问题Mongo
        questionService.deleteQuestionByUid(id);

        List<PostMessage> pmList = postMessageService.getAllPostMessage();
        // 删除用户的点赞、收藏
        for (PostMessage pm:pmList){
            postMessageService.deleteLikeByUid(pm.getpId(), id);
            postMessageService.deleteFavoriteById(pm.getpId(), id);
        }
        // 删除评论
        List<PostMessageText> pmtList = postMessageTextService.getAllPostMessageText();
        for (PostMessageText pmt:pmtList){
            List<CommentNode> commentNodes = pmt.getComments();
            if (commentNodes != null){
                for (int i=0; i<commentNodes.size(); i++){
                    CommentNode c = commentNodes.get(i);
                    if (c.getUserId().equals(id)){
                        commentNodes.remove(i);
                    }
                }
                pmt.setComments(commentNodes);
                postMessageTextService.addItem(pmt);
            }
        }

        List<Question> qList = questionService.getAllQuestion();
        // 删除用户的点赞、收藏
        for (Question q:qList){
            questionService.deleteLikeByUid(q.getqId(), id);
            questionService.deleteFavoriteByUid(q.getqId(), id);
        }
        // 删除回答
        List<QuestionText> qtList = questionTextService.getAllQuestionText();
        for (QuestionText qt:qtList){
            List<CommentNode> commentNodes = qt.getAnswers();
            if (commentNodes != null){
                for (int i=0; i<commentNodes.size(); i++){
                    CommentNode c = commentNodes.get(i);
                    if (c.getUserId().equals(id)){
                        commentNodes.remove(i);
                    }
                }
                qt.setAnswers(commentNodes);
                questionTextService.addItem(qt);
            }
        }
    }

    /**
     * 更新用户信息
     * @param userId
     * @param nickname
     * @param sex
     * @param avatarId
     * @param signature
     * @param mailbox
     * @param labels
     * @throws NullPointerException
     */
    @Transactional
    public void updateUser(String userId, String nickname,
                           boolean sex, String avatarId,
                           String signature, String mailbox,
                           List<String> labels) throws NullPointerException{
        // ES对象修改
        UserInfo userInfo = userInfoService.getUserById(userId);
        if (userInfo == null){
            throw new NullPointerException();
        }
        userInfo.setNickname(nickname);
        userInfo.setSignature(signature);
        userInfo.setLabels(labels);
        userInfoService.addItem(userInfo);
        // mongoDB对象修改
        User user = userService.getUserById(userId);
        user.setSex(sex);
        user.setAvatarId(avatarId);
        user.setMailbox(mailbox);
        userService.addUser(user);
    }

    /**
     * 判断用户是否以及存在
     * @param userId
     * @return
     */
    public boolean userIsExist(String userId){
        if (userService.getUserById(userId) != null){
            return true;
        } else{
            return false;
        }
    }

    /**
     * 判断账户密码是否正确
     * @param userId
     * @param password
     * @return
     */
    public boolean passwordIsCorrect(String userId, String password){
        if (userService.getUserById(userId).getPassword().equals(password)){
            return true;
        } else{
            return false;
        }
    }

    /**
     * 获得DTO对象
     * @param userInfo
     * @param user
     * @return
     */
    public UserDTO getDTO(UserInfo userInfo, User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setNickname(userInfo.getNickname());
        userDTO.setPassword(user.getPassword());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setSex(user.isSex());
        userDTO.setRegisterDate(user.getRegisterDate());
        userDTO.setAvatarId(user.getAvatarId());
        userDTO.setSignature(userInfo.getSignature());
        userDTO.setMailbox(user.getMailbox());
        userDTO.setCoverPicture(user.getCoverPicture());
        userDTO.setLabels(userInfo.getLabels());
        return userDTO;
    }

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    public UserDTO getUserById(String userId){
        UserDTO userDTO = new UserDTO();
        // mongoDB信息
        User user = userService.getUserById(userId);
        // ES信息
        UserInfo userInfo = userInfoService.getUserById(userId);

        return getDTO(userInfo,user);
    }

    /**
     * 获取用户资料
     * @param userId
     * @return
     */
    public JSONObject getUserInfo(String userId){
        UserInfo userInfo = userInfoService.getUserById(userId);
        User user = userService.getUserById(userId);

        JSONObject result = new JSONObject();
        result.put("userBasicInfo",getDTO(userInfo,user));
        result.put("articleCount", postMessageService.getByUserId(userId).size());
        result.put("questionCount", questionService.getByUserId(userId).size());
        result.put("attentCount",userService.getAllFollows(userId).size());
        result.put("fansCount",userService.getAllFans(userId).size());

        return result;
    }

    /**
     * 获取全部用户
     * @return
     */
    public List<UserDTO> getAllUsers(){
        List<UserInfo> userInfos = userInfoService.getAllUsers();
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (int i=0; i<users.size(); i++){
            UserInfo userInfo = userInfos.get(i);
            User user = users.get(i);
            userDTOS.add(getDTO(userInfo, user));
        }

        return userDTOS;
    }

    /**
     * 获取用户的全部关注
     * @param userId
     */
    public List<Object> getAllFollows(String userId){
        List<Object> result = new ArrayList<>();
        JSONObject item;
        List<String> followsList = userService.getAllFollows(userId);
        for (String id:followsList){
            UserInfo userInfo = userInfoService.getUserById(id);
            User user = userService.getUserById(id);
            item = new JSONObject();
            item.put("uid",userInfo.getUserId());
            item.put("avatar",user.getAvatarId());
            item.put("nickname",userInfo.getNickname());
            item.put("signature",userInfo.getSignature());
            result.add(item);
        }
        return result;
    }

    /**
     * 获取用户的全部粉丝
     * @param userId
     */
    public List<Object> getAllFans(String userId){
        List<Object> result = new ArrayList<>();
        JSONObject item;
        List<String> fansList = userService.getAllFans(userId);
        for (String id:fansList){
            UserInfo userInfo = userInfoService.getUserById(id);
            User user = userService.getUserById(id);
            item = new JSONObject();
            item.put("uid",userInfo.getUserId());
            item.put("avatar",user.getAvatarId());
            item.put("nickname",userInfo.getNickname());
            item.put("signature",userInfo.getSignature());
            item.put("isAttent",userService.isFollow(userId, id));
            result.add(item);
        }
        return result;
    }
}
