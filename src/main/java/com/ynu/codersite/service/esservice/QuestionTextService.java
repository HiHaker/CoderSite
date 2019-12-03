package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/12/2 0002
 * BY Jianlong
 */
@Service
public class QuestionTextService {
    @Autowired
    QuestionTextRepository questionTextRepository;

    /**
     * 增加一条问题文本记录
     * @param questionText
     */
    public void addItem(QuestionText questionText){
        questionTextRepository.save(questionText);
    }

    /**
     * 根据问题文本记录删除对应的项
     * @param id
     */
    public void deleteItem(String id){
        questionTextRepository.deleteById(id);
    }
}
