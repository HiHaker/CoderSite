package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Service
public class PostMessageTextService {
    @Autowired
    PostMessageTextRepository postMessageTextRepository;

    /**
     * 增加一条文章文本记录
     * @param postMessageText
     */
    public void addItem(PostMessageText postMessageText){
        postMessageTextRepository.save(postMessageText);
    }

    /**
     * 根据文章文本记录删除对应的项
     * @param id
     */
    public void deleteItem(String id){
        postMessageTextRepository.deleteById(id);
    }
}
