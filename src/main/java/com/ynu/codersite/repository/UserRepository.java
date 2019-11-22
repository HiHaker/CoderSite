package com.ynu.codersite.repository;

import com.ynu.codersite.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUserId(String userID);
}
