package com.ynu.codersite;

import com.ynu.codersite.entity.QuestionDTO;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.service.AQuestionService;
import com.ynu.codersite.service.esservice.QuestionTextService;
import com.ynu.codersite.service.mongoservice.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/8 0008
 * BY Jianlong
 */
@SpringBootTest
public class QuestionTest {
    @Autowired
    AQuestionService aQuestionService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionTextService questionTextService;

    // 增加一条问题
    @Test
    void addQuestion(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQid("001");
        questionDTO.setUid("001");
        questionDTO.setTitle("怎么做?");
        questionDTO.setContent("lalalala");
        questionDTO.setPostTime("2019-12-8");
        List<String> labels = new ArrayList<>();
        labels.add("Java");
        questionDTO.setLabels(labels);
        List<String> imgs = new ArrayList<>();
        imgs.add("001");
        questionDTO.setImages(imgs);

        aQuestionService.addQuestion(questionDTO);
    }

    /**
     * 删除一条问题
     */
    @Test
    void deleteQuestion(){
        aQuestionService.deleteQuestion("001");
    }

    /**
     * 增加一条点赞记录
     */
    @Test
    void addLike(){
        questionService.addLike("001","001","001","2019-12-8");
    }

    /**
     * 删除点赞
     */
    @Test
    void testDeleteLike(){
        questionService.deleteLikeById("001","001");
    }

    /**
     * 增加收藏
     */
    @Test
    void addFavorite(){
        questionService.addFavorite("001","001","001","2019-12-8");
    }

    /**
     * 删除收藏
     */
    @Test
    void testDeleteFavorite(){
        questionService.deleteFavoriteById("001","001");
    }

    @Test
    // 增加评论测试
    void addComment(){
        questionTextService.addAnswer("001","001","001","我会","2019-12-8");
    }


    // 删除评论测试
    @Test
    void deleteComment(){
        questionTextService.deleteAnswer("001","001");
    }

    @Test
    void getNewestQuestionByKeyword(){
        List<QuestionText> result = questionTextService.getNewestQuestionByKeyword("安装",0);
        for (QuestionText qt:result){
            System.out.println(qt);
        }
    }
}