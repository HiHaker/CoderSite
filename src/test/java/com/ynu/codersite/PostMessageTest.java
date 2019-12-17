package com.ynu.codersite;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.ContentNode;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.mogoentity.PostMessage;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import com.ynu.codersite.service.APostMessageService;
import com.ynu.codersite.service.esservice.PostMessageTextService;
import com.ynu.codersite.service.mongoservice.PostMessageService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/4 0004
 * BY Jianlong
 */
@SpringBootTest
public class PostMessageTest {

    @Autowired
    APostMessageService aPostMessageService;
    @Autowired
    PostMessageService postMessageService;
    @Autowired
    PostMessageTextService postMessageTextService;
    @Autowired
    PostMessageTextRepository postMessageTextRepository;
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    // 发表帖子测试
    void testPostPost(){
        PostMessageDTO data = new PostMessageDTO();
        data.setAid("001");
        data.setUid("001");
        data.setTitle("欢迎来到程序员的世界");
        data.setPostTime("2019-12-08");
        List<ContentNode> content = new ArrayList<>();
        content.add(new ContentNode("你好","001"));
        data.setContent(content);
        List<String> labels = new ArrayList<>();
        labels.add("欢迎");
        data.setLabels(labels);
        aPostMessageService.addPostMessage(data);
    }

    @Test
    // 删除帖子测试
    void testDeletePostById(){
        aPostMessageService.deleteById("001");
    }

    // 失败记录1-字段没能设置为id，使用JPA删除时找不到属性，未能删除成功

    @Test
    // 子测试-删除mongo数据库
    void testDeletePostMongo(){
        postMessageService.deletePostMessageById("5de7bd73ca00733b860e1a74");
    }

    @Test
    // 增加一条点赞记录测试
    void testAddLikes(){
        postMessageService.addLike("001","001","002","2019-12-5");
    }

    @Test
    void deleteLike(){
        postMessageService.deleteLikeById("001","001");
    }

    @Test
    // 子测试
    void testGetById(){
        System.out.println(postMessageService.getPostMessageById("001"));
    }

    // 失败记录，没有考虑刚开始时子文档likes没有对象为空值的情况，造成了空指针错误
    // 失败记录，由于增加点赞需要先根据文章id进行查询，如果输入了错误的文章id就会造成查询为空，造成了空指针错误

    @Test
    // 子文档删除测试
    void testDeleteLike(){
        postMessageService.deleteLikeById("001","002");
    }

    @Test
    // 增加一条收藏记录测试
    void testAddFavorite(){
        postMessageService.addFavorite("001","001","001","2019-12-5");
    }

    @Test
    // 删除一条收藏测试
    void testDeleteFavorite(){
        postMessageService.deleteFavoriteById("001","001");
    }

    @Test
    // 增加评论测试
    void addComment(){
        postMessageTextService.addComment("001","001","001","哈哈哈哈","2019-12-8");
    }

    // 删除评论测试
    @Test
    void deleteComment(){
        postMessageTextService.deleteComment("001","001");
    }

    @Test
    void pageQuery(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("postmessagetext")//索引名
                .withQuery(QueryBuilders.matchQuery("title", "如何")) // 查询条件使用matchquery
                .withPageable(PageRequest.of(0, 2))//从0页开始查，每页1个结果
                .build();
        List<PostMessageText> result = elasticsearchRestTemplate.queryForList(searchQuery, PostMessageText.class);
        for (PostMessageText pt: result){
            System.out.println(pt);
        }
    }

    @Test
    void pageQueryJPA(){
        Page<PostMessageText> result = postMessageTextRepository.findByTitle("如何", PageRequest.of(0, 2));
        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
        System.out.println(result.getContent());
    }

    @Test
    void pageOrderQueryJPA(){
        Page<PostMessageText> result = postMessageTextRepository.findByOrderByPostTimeDesc(PageRequest.of(0, 10));
        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
        System.out.println(result.getContent().get(0));
    }

    @Test
    void getNewestPostMessage(){
        List<JSONObject> result = aPostMessageService.getNewestPostMessage(0);
        System.out.println(result.get(0).get("aid"));
    }

    @Test
    void getFollowsNewestPM(){
        aPostMessageService.getFollowsNewestPM("001");
    }

    @Test
   void getUserNewestPM(){
        List<PostMessage> result = postMessageService.getUserNewestPM("001",0);
        for (PostMessage pm:result){
            System.out.println(pm);
        }
   }

   @Test
   void getNewestPostMessageByKeyword(){
        List<PostMessageText> result = postMessageTextService.getNewestPostMessageByKeyword("学习",0);
        if (result == null){
            System.out.println("null");
        }
        for (PostMessageText pmt:result){
            System.out.println(pmt);
        }
   }

   @Test
   void pageQueryByLabel(){
       Page<PostMessageText> result = postMessageTextRepository.findByLabelsContainsOrderByPostTimeDesc("坚持",PageRequest.of(0, 10));
       System.out.println(result.getTotalPages());
       System.out.println(result.getTotalElements());
       System.out.println(result.getContent().get(0));
   }

   @Test
   void deleteLikeByUid(){
        postMessageService.deleteLikeByUid("002","001");
   }

   @Test
   void deleteByUid(){
       List<PostMessage> postMessages = postMessageService.getByUserId("001");
       System.out.println(postMessages.size());
       for (PostMessage p:postMessages){
           System.out.println(p.getpId());
           postMessageTextService.deleteItem(p.getpId());
       }
   }
}