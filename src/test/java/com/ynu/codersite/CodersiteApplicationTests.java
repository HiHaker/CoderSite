package com.ynu.codersite;

import com.ynu.codersite.entity.esentity.QuestionText;
import com.ynu.codersite.repository.esrepoitory.QuestionTextRepository;
import com.ynu.codersite.service.esservice.QuestionTextService;
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

@SpringBootTest
class CodersiteApplicationTests {

//    @Qualifier("getClient")
    @Autowired
    RestHighLevelClient client;
    @Autowired
    QuestionTextRepository questionTextRepository;
    @Autowired
    QuestionTextService qs;

    @Autowired
    ElasticsearchRestTemplate template;

    @Test
    void esTest(){
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
    void contextLoads() {
    }

}
