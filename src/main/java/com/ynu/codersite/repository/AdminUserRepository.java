package com.ynu.codersite.repository;

import com.ynu.codersite.entity.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/11/22 0022
 * BY Jianlong
 */
@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, String> {
    AdminUser findAdminUserByAdminId(String adminId);
    void deleteAdminUserByAdminId(String adminId);
}
