package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.repository.esrepoitory.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Service
public class UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 增加一条用户信息文本记录
     * @param
     */
    public void addItem(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }

    /**
     * 根据用户文本记录删除对应的项
     * @param id
     */
    public void deleteItem(String id){
        userInfoRepository.deleteById(id);
    }
}
