package com.ynu.codersite.repository.esrepoitory;

import com.ynu.codersite.entity.esentity.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo, String> {
    List<UserInfo> findByLabels(String keyword);
}