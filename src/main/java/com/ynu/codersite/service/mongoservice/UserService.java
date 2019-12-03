package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.repository.mongorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * 增加一个用户
     * @param user
     */
    public void addUser(User user){
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
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user){
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
}
