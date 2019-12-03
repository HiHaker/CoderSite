package com.ynu.codersite.repository.mongorepository;

import com.ynu.codersite.entity.mogoentity.PostMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
public interface PostMessageRepository extends MongoRepository<PostMessage, String> {
    void deletePostMessageByPId(String pId);
    PostMessage findPostMessageByPId(String pId);
}
