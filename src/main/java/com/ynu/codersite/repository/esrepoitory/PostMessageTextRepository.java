package com.ynu.codersite.repository.esrepoitory;

import com.ynu.codersite.entity.esentity.PostMessageText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
public interface PostMessageTextRepository extends ElasticsearchRepository<PostMessageText, String> {
    Page<PostMessageText> findByTitle(String key1, Pageable of);
    Page<PostMessageText> findByTitleOrderByPostTimeAsc(String key1, Pageable of);
    // 根据发表时间排序（最新）分页查询
    Page<PostMessageText> findByOrderByPostTimeDesc(Pageable of);
    Page<PostMessageText> findByLabelsContainsOrderByPostTimeDesc(String keyword, Pageable of);
}