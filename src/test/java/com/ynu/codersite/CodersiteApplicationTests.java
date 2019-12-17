package com.ynu.codersite;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.ContentNode;
import com.ynu.codersite.entity.PostMessageDTO;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import com.ynu.codersite.repository.esrepoitory.UserInfoRepository;
import com.ynu.codersite.service.esservice.QuestionTextService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.*;

import java.io.IOException;
import java.util.*;

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

    // ESsearchQuery
    void ESQuery(){
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

    // 嵌套对象查询
    void nestQuery(){
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

    // 字符串数组查询
    void listQuery(){
        List<UserInfo> result = userInfoRepository.findByLabels("Python");
        System.out.println(result);
    }

    void nestDelete(){
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setIndex("postmessagetext");
        deleteQuery.setType("postMessageText");
        deleteQuery.setQuery(QueryBuilders.nestedQuery("comments",QueryBuilders.matchQuery("comments.id","001"), ScoreMode.Min));
        template.delete(deleteQuery, CommentNode.class);
//        template.delete(deleteQuery);
    }

    // 嵌套对象删除
    void nestDelete1(){
        System.out.println("hello");
        UpdateQuery query = new UpdateQuery();
        UpdateRequest request = new UpdateRequest();
        request.index("postmessagetext");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", "001");
        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.comments.remove(ctx._source.comments.id = id);", jsonMap);
        request.script(inline);
        if (request == null)
            System.out.println("request==null");
        if (query == null)
            System.out.println("query==null");
        query.setIndexName("postmessagetext");
        query.setType("postMessageText");
        query.setId("005");
        query.setUpdateRequest(request);
        template.update(query);
    }

}