package com.felix.forum.service;

import com.felix.forum.po.Article;
import com.felix.forum.po.Tag;
import com.felix.forum.vo.ArticleQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    Article getArticle(Long id);

    Article getAndConvert(Long id);




    List<Article> listArticle(Map<String,Object> map);
    int count(Map<String, Object> params);

    List<Article> listArticle(String query);

    List<Article> listRecommendArticleTop(Integer size);

    Map<String,List<Article>> archiveArticle();

    int countArticle();

    int saveArticle(Article article,List<Tag> tgs);

    int updateArticle(Long id, Article article,List<Tag> tgs);

    void deleteArticle(Long id);
    
    
    List<Article> listArticlebytag(Map<String,Object> map);
    
    List<Article> listArticlebytype(Map<String,Object> map);
}
