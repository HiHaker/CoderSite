package com.ynu.codersite.repository.mongorepository;

import com.ynu.codersite.entity.mogoentity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2019/11/21 0021
 * BY Jianlong
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUserId(String userID);
    void deleteUserByUserId(String userId);
    List<User> findUsersByFollowsContains(String userId);
    User findUserByUserIdAndFollowsContains(String userId, String objId);
}
