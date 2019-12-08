package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.repository.mongorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 增加一个用户
     * @param user
     */
    public void addUser(User user){
        userRepository.save(user);
    }

    /**
     * 关注一个用户，用户userId关注objId
     * @param userId
     * @param objId
     */
    public void addFollow(String userId, String objId){
        User user = this.getUserById(userId);
        if (user.getFollows() == null){
            List<String> follows = new ArrayList<>();
            follows.add(objId);
            user.setFollows(follows);
        } else{
            user.getFollows().add(objId);
        }
        userRepository.save(user);
    }

    /**
     * 删除一个用户
     * @param userId
     */
    public void deleteUserById(String userId){
        userRepository.deleteUserByUserId(userId);
    }

    /**
     * 删除一个用户关注
     * @param userId
     * @param objId
     */
    public void deleteFollow(String userId, String objId){
        User user = this.getUserById(userId);
        List<String> follows = user.getFollows();
        for (int i=0; i<follows.size(); i++){
            if (follows.get(i).equals(objId)){
                follows.remove(i);
                break;
            }
        }
        user.setFollows(follows);
        userRepository.save(user);
    }

    /**
     * 根据用户的id查询用户
     * @param userId
     * @return
     */
    public User getUserById(String userId){
        return userRepository.findUserByUserId(userId);
    }

    /**
     * 获取用户的全部关注
     * @param userId
     * @return
     */
    public List<String> getAllFollows(String userId){
        return this.getUserById(userId).getFollows();
    }

    /**
     * 判断用户userId是否关注了用户objId
     * @param userId
     * @param objId
     * @return
     */
    public boolean isFollow(String userId, String objId){
        if (userRepository.findUserByUserIdAndFollowsContains(userId, objId) == null){
            return false;
        } else{
            return true;
        }
    }

    /**
     * 获取用户的全部粉丝的id列表
     * @param userId
     * @return
     */
    public List<String> getAllFans(String userId){
        List<User> fans = userRepository.findUsersByFollowsContains(userId);
        List<String> fansList = new ArrayList<>();
        for (User u:fans){
            fansList.add(u.getUserId());
        }
        return fansList;
    }
}
