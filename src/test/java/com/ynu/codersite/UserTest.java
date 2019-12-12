package com.ynu.codersite;

import com.ynu.codersite.entity.UserDTO;
import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.repository.mongorepository.UserRepository;
import com.ynu.codersite.service.AUserService;
import com.ynu.codersite.service.esservice.UserInfoService;
import com.ynu.codersite.service.mongoservice.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/8 0008
 * BY Jianlong
 */
@SpringBootTest
public class UserTest {
    @Autowired
    AUserService aUserService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserInfoService userInfoService;

    // 增加一个用户测试
    @Test
    void addUser(){
        List<String> labels = new ArrayList<>();
        labels.add("测试");
        UserDTO userDTO = new UserDTO(
                "002","测试人员002",
                "123456","1999-05-29",
                true,"2019-12-08",
                "001","测试",
                "1434938035@qq.com","001",labels);
        aUserService.addUser(userDTO);
    }

    // 删除一个用户测试
    @Test
    void deleteUser(){
        aUserService.deleteUser("001");
    }

    // 更新用户测试
    @Test
    void updateUser(){
        String userId = "001";
        String nickname = "测试人员0";
        boolean sex = false;
        String avatarId = "002";
        String signature = "happy";
        String mailbox = "1434938035@163.com";
        List<String> labels = new ArrayList<>();
        labels.add("测试用例");

        aUserService.updateUser(userId,nickname,sex,avatarId,signature,mailbox,labels);
    }

    // 关注用户测试
    @Test
    void followUser(){
        userService.addFollow("002","001");
    }

    // 删除关注测试
    @Test
    void deleteFollow(){
        userService.deleteFollow("001","002");
    }

    // 得到用户信息
    @Test
    void getUserInfo(){
        System.out.println(aUserService.getUserById("001"));
    }

    // 获取用户粉丝测试
    @Test
    void getFans(){
        List<Object> list = aUserService.getAllFans("001");
        System.out.println(list.size());
        System.out.println(list.get(0));
    }

    // 获取用户关注测试
    @Test
    void getFollows(){
        List<Object> list = aUserService.getAllFollows("002");
        System.out.println(list.size());
        System.out.println(list.get(0));
    }

    // 查询关注记录测试
    @Test
    void getFollowRecord(){
        System.out.println(userService.isFollow("001","002"));
    }

    @Test
    void getAllUsersES(){
        List<UserInfo> users = userInfoService.getAllUsers();
        for (UserInfo u:users){
            System.out.println(u);
        }
    }

    @Test
    void getAllUsersMongo(){
        List<User> users = userService.getAllUsers();
        for (User u:users){
            System.out.println(u);
        }
    }
}