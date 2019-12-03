package com.ynu.codersite.repository.esrepoitory;

import com.ynu.codersite.entity.esentity.QuestionText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created on 2019/12/2 0002
 * BY Jianlong
 */
public interface QuestionTextRepository extends ElasticsearchRepository<QuestionText, String> {
    Page<QuestionText> findByTitleLike(String title, Pageable page);
}