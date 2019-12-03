package com.ynu.codersite.repository.esrepoitory;

import com.ynu.codersite.entity.esentity.PostMessageText;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
public interface PostMessageTextRepository extends ElasticsearchRepository<PostMessageText, String> {
}
