package com.ynu.codersite.repository;

import com.ynu.codersite.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2019/11/28 0028
 * BY Jianlong
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    void deleteQuestionByQId(String qId);
    Question findQuestionByQId(String qId);
}
