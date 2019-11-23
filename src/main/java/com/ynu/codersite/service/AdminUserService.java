package com.ynu.codersite.service;

import com.ynu.codersite.entity.AdminUser;
import com.ynu.codersite.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
@Service
public class AdminUserService {
    @Autowired
    AdminUserRepository adminUserRepository;

    /**
     * 增加一个管理员
     * @param adminUser
     */
    public void addAdminUser(AdminUser adminUser){
        adminUserRepository.save(adminUser);
    }

    /**
     * 删除一个管理员
     * @param adminUserId
     */
    public void deleteAdminUser(String adminUserId){
        adminUserRepository.deleteAdminUserByAdminId(adminUserId);
    }

    /**
     * 查询一个管理员
     * @param adminUserId
     * @return
     */
    public AdminUser getAdminUserById(String adminUserId){
        return adminUserRepository.findAdminUserByAdminId(adminUserId);
    }
}
