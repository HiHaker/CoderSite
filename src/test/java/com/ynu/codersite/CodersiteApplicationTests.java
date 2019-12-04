package com.ynu.codersite;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.ContentNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import com.ynu.codersite.repository.esrepoitory.UserInfoRepository;
import com.ynu.codersite.service.esservice.QuestionTextService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CodersiteApplicationTests {

//    @Qualifier("getClient")
    @Autowired
    RestHighLevelClient client;
    @Autowired
    QuestionTextRepository questionTextRepository;
    @Autowired
    PostMessageTextRepository postMessageTextRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    QuestionTextService qs;

    @Autowired
    ElasticsearchRestTemplate template;

    @Test
    void esTest1(){
//        QuestionText qt = new QuestionText();
//        qt.setqId("001");
//        qt.setTitle("SpringBoot如何集成Elasticsearch");
//        qt.setContent("可以查看官方文档，使用新版的连接方式");
//
//        qs.addItem(qt);
//
//        QuestionText qt1 = new QuestionText();
//        qt1.setqId("002");
//        qt1.setTitle("如何学习好Python");
//        qt1.setContent("可以查看廖雪峰的官方网站进行学习");
//
//        qs.addItem(qt1);
//
//        QuestionText qt2 = new QuestionText();
//        qt2.setqId("003");
//        qt2.setTitle("如何学习好SpringBoot");
//        qt2.setContent("可以查看SpringBoot的官方网站进行学习");
//
//        qs.addItem(qt2);

//        qs.deleteItem("");
//        System.out.println(questionTextRepository.findByTitleLike("spring"));
//        template
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("codersite")//索引名
                .withQuery(QueryBuilders.matchQuery("title", "如何")) // 查询条件使用matchquery
//                .withQuery(QueryBuilders.termQuery("title", "Python"))//查询条件，这里简单使用term查询
                .withPageable(PageRequest.of(0, 1))//从0页开始查，每页10个结果
                .build();

        ScrolledPage<QuestionText> scroll = template.startScroll(3000, searchQuery, QuestionText.class);
        System.out.println("查询总命中数：" + scroll.getTotalElements());
        while (scroll.hasContent()) {
            for (QuestionText qt : scroll.getContent()) {
                //Do your work here
                System.out.println(qt);
            }
            //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
            scroll = template.continueScroll(scroll.getScrollId(), 3000, QuestionText.class);
        }
        //及时释放es服务器资源
        template.clearScroll(scroll.getScrollId());
    }

    @Test
    void esTest2(){
        PostMessageText test = new PostMessageText();
        test.setpId("001");
        test.setTitle("记录第一次使用Spring");
        List<CommentNode> comments = new ArrayList<>();
        CommentNode node1 = new CommentNode();
        node1.setUserId("001");
        node1.setContent("哇塞哇噻");
        node1.setTime("2019-12-04");
        comments.add(node1);
        test.setComments(comments);
        List<ContentNode> contents = new ArrayList<>();
        ContentNode node2 = new ContentNode();
        node2.setPara("第一次使用Spring，仿佛进入了新世界~");
        node2.setImage("1.jpg");
        contents.add(node2);
        test.setContent(contents);
        postMessageTextRepository.save(test);
    }

    // 嵌套对象查询
    @Test
    void esTest3(){
        System.out.println("hello");
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("postmessagetext")//索引名
                // 使用nestedQuery进行嵌套查询
                .withQuery(QueryBuilders.nestedQuery("content",QueryBuilders.matchQuery("content.para","Spring"),ScoreMode.Total)) // 查询条件使用matchquery
//                .withQuery(QueryBuilders.matchQuery("content", "Spring")) // 查询条件使用matchquery
//                .withQuery(QueryBuilders.termQuery("title", "Python"))//查询条件，这里简单使用term查询
//                .withPageable(PageRequest.of(0, 1))//从0页开始查，每页10个结果
                .build();
        List<PostMessageText> result = template.queryForList(searchQuery, PostMessageText.class);
        System.out.println("hello2");
        System.out.println(result.get(0));
        System.out.println("hello3");
    }

    @Test
    void esTest4(){
        UserInfo test = new UserInfo();
        test.setNickname("花花2");
        test.setSignature("我是最棒的!");
        test.setUserId("002");
        List<String> label = new ArrayList<>();
        label.add("Python");
        test.setLabels(label);
        userInfoRepository.save(test);
    }


    // 字符串数组查询
    @Test
    void esTest5(){
        List<UserInfo> result = userInfoRepository.findByLabels("Python");
        System.out.println(result);
    }

    @Test
    void contextLoads() {
    }

}
