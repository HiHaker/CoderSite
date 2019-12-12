package com.ynu.codersite.service.esservice;

import com.ynu.codersite.entity.CommentNode;
import com.ynu.codersite.entity.esentity.PostMessageText;
import com.ynu.codersite.repository.esrepoitory.PostMessageTextRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/12/3 0003
 * BY Jianlong
 */
@Service
public class PostMessageTextService {
    @Autowired
    PostMessageTextRepository postMessageTextRepository;
    @Autowired
    ElasticsearchRestTemplate template;

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

    /**
     * 增加一条评论记录
     * @param aid
     * @param id
     * @param uid
     * @param content
     * @param time
     */
    public void addComment(String aid, String id, String uid, String content, String time){
        PostMessageText postMessageText = postMessageTextRepository.findById(aid).orElse(null);
        CommentNode commentNode = new CommentNode(id,uid,content,time);
        if (postMessageText.getComments() == null){
            List<CommentNode> comments = new ArrayList<>();
            comments.add(commentNode);
            postMessageText.setComments(comments);
        } else{
            postMessageText.getComments().add(commentNode);
        }
        postMessageTextRepository.save(postMessageText);
    }

    /**
     * 删除一条评论记录
     * @param aid
     * @param id
     */
    public void deleteComment(String aid, String id){
        PostMessageText postMessageText = postMessageTextRepository.findById(aid).orElse(null);
        List<CommentNode> comments = postMessageText.getComments();
        for (int i=0; i<comments.size(); i++){
            if (comments.get(i).getId().equals(id)){
                comments.remove(i);
                break;
            }
        }
        postMessageText.setComments(comments);
        postMessageTextRepository.save(postMessageText);
    }

    /**
     * 获取全部帖子文本
     * @return
     */
    public List<PostMessageText> getAllPostMessageText(){
        return postMessageTextRepository.findAll();
    }

    /**
     * 根据id查询对象
     * @param pId
     * @return
     */
    public PostMessageText getById(String pId){
        return postMessageTextRepository.findById(pId).orElse(null);
    }

    /**
     * 分页查询最新的10条文章
     * @param page
     * @return
     */
    public List<PostMessageText> getNewestPostMessage(Integer page){
        Page<PostMessageText> pageResult = postMessageTextRepository.findByOrderByPostTimeDesc(PageRequest.of(page, 10));
        return pageResult.getContent();
    }

    /**
     * 根据关键词查询最新的文章（标题和内容）
     * @param keyword
     * @param page
     * @return
     */
    public List<PostMessageText> getNewestPostMessageByKeyword(String keyword, Integer page){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("postmessagetext")//索引名
                .withQuery(QueryBuilders.matchQuery("title", keyword))
                // 使用nestedQuery进行嵌套查询
                .withQuery(QueryBuilders.nestedQuery("content",QueryBuilders.matchQuery("content.para",keyword), ScoreMode.Total))
                .withPageable(PageRequest.of(page, 10))//从0页开始查，每页10个结果
                .build();
        List<PostMessageText> result = template.queryForList(searchQuery, PostMessageText.class);
        return result;
    }

    /**
     * 根据关键词查询最新的文章(标签)
     * @param keyword
     * @param page
     * @return
     */
    public List<PostMessageText> getNewestPMByLabel(String keyword, Integer page){
        Page<PostMessageText> result = postMessageTextRepository.findByLabelsContainsOrderByPostTimeDesc(keyword,PageRequest.of(page, 10));
        return result.getContent();
    }
}