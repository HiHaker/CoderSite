package com.ynu.codersite.service;

import com.ynu.codersite.entity.User;
import com.ynu.codersite.repository.UserRepository;
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
     * 根据用户的id查询用户
     * @param userID
     * @return
     */
    public User getUserById(String userID){
        return userRepository.findUserByUserId(userID);
    }
}
