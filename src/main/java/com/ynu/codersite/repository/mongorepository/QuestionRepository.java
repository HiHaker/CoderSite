package com.ynu.codersite.repository.mongorepository;

import com.ynu.codersite.entity.mogoentity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    void deleteByUserId(String uid);
    Page<Question> findByUserIdOrderByPostTimeDesc(String userId, Pageable of);
    List<Question> findByUserId(String userId);
}
