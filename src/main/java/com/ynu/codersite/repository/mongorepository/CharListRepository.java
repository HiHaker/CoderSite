package com.ynu.codersite.repository.mongorepository;

import com.ynu.codersite.entity.mogoentity.ChatList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/12/17 0017
 * BY Jianlong
 */
@Repository
public interface CharListRepository extends MongoRepository<ChatList, String> {
}
