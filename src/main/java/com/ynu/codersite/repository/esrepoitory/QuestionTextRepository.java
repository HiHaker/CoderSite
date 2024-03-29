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
    List<QuestionText> findAll();
    Page<QuestionText> findByTitleLike(String title, Pageable page);
    Page<QuestionText> findByOrderByPostTimeDesc(Pageable page);
    Page<QuestionText> findByTitleLikeOrContentLikeOrderByPostTimeDesc(String keyword1, String keywod2, Pageable page);
    Page<QuestionText> findByLabelsContainsOrderByPostTimeDesc(String keyword, Pageable page);
}