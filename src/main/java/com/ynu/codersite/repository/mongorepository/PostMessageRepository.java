package com.ynu.codersite.repository.mongorepository;

import com.ynu.codersite.entity.mogoentity.PostMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
public interface PostMessageRepository extends MongoRepository<PostMessage, String> {
    void deleteByUserId(String uid);
    Page<PostMessage> findByUserIdOrderByPostTimeDesc(String userId, Pageable of);
    List<PostMessage> findByUserId(String userId);
}