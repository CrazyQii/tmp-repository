package com.felix.forum.service;

import com.felix.forum.NotFoundException;
import com.felix.forum.dao.ArticleRepository;
import com.felix.forum.po.Article;
import com.felix.forum.po.Comment;
import com.felix.forum.po.Tag;
import com.felix.forum.po.Type;
import com.felix.forum.po.User;
import com.felix.forum.util.MarkdownUtils;
import com.felix.forum.util.MyBeanUtils;
import com.felix.forum.vo.ArticleQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Administrator
 * @date 2022/02/18 12:10
 **/
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private CommentService commentService;

    @Override
    public Article getArticle(Long id) {
        return articleRepository.getOne(id);
    }

    @Transactional
    @Override
    public Article getAndConvert(Long id) {
        Article ar = articleRepository.getOne(id);
        
        
        Type type =typeService.getType(Long.parseLong(ar.getTypeId()));
    	ar.setType(type);
    	System.out.print(ar.getTypeId());
    	User user = userService.getUserInfo(Long.parseLong(ar.getUserId()));
    	ar.setUser(user);
    	

    	List<Comment>  lco = commentService.listCommentByArticleId(ar.getId());
    	ar.setComments(lco);
    	
    	List<Tag> tags = new ArrayList<>();
    	String ts="";
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("articles_id", ar.getId());
    	List<String> tagids =  tagService.findtags(map);     
    	for(String aa : tagids) {
    		Tag tag = tagService.getTag(Long.parseLong(aa));
    		tags.add(tag);
    		ts=ts+tag.getId()+",";
    	}
    	ar.setTagIds(ts);
    	
    	ar.setTags(tags);
        
        
        if (ar == null) {
            throw new NotFoundException("该文献不存在！");
        }
        Article article1 = new Article();
        BeanUtils.copyProperties(ar, article1);
        String content = article1.getContent();
        article1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        articleRepository.updateViews(id);
        return article1;
    }



    @Override
    public List<Article> listArticle(Map<String,Object> map) {
    	
    	List<Article> list =  articleRepository.findAll(map); 
    	
    	for(Article ar : list) {
        	
        	Type type =typeService.getType(Long.parseLong(ar.getTypeId()));
        	ar.setType(type);
        	System.out.print(ar.getTypeId());
        	
   
        	User user = userService.getUserInfo(Long.parseLong(ar.getUserId()));
        	ar.setUser(user);

        	List<Comment>  lco = commentService.listCommentByArticleId(ar.getId());
        	ar.setComments(lco);
        	
        	List<Tag> tags = new ArrayList<>();
        	String ts="";
        	Map<String,Object> map3 = new HashMap<String,Object>();
        	map3.put("articles_id", ar.getId());
        	List<String> tagids =  tagService.findtags(map3);     
        	for(String aa : tagids) {
        		Tag tag = tagService.getTag(Long.parseLong(aa));
        		tags.add(tag);
        		ts=ts+tag.getId()+",";
        	}
        	ar.setTagIds(ts);
        	
        	ar.setTags(tags);
        	
        	
        }
		
		return list;
    }



    @Override
    public List<Article> listArticle(String query) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("query", query);
    	
        return articleRepository.findByQuery(map);
    }

    @Override
    public List<Article> listRecommendArticleTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
//        Pageable pageable = new PageRequest(0,size,sort);
        Pageable pageable = PageRequest.of(0, size, sort);
        return articleRepository.findTop();
    }

    @Override
    public Map<String, List<Article>> archiveArticle() {
        List<String> years = articleRepository.findGroupYear();
        Map<String,List<Article>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,articleRepository.findByYear(year));
        }
        return null;
    }

    @Override
    public int countArticle() {
        return articleRepository.count(); 
    }

    @Transactional
    @Override
    public int saveArticle(Article article,List<Tag> tgs) {
        if (article.getId() == null) {
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            article.setViews(0);
        } else {
            article.setUpdateTime(new Date());
        }
        int a =  articleRepository.save(article);
        

        for(Tag tg:tgs) {
        	 Map<String,Object> map = new HashMap<String,Object>();
        	 map.put("articles_id", article.getId());
        	 map.put("tags_id", tg.getId());
        	 tagService.saveaTag(map);
        }
       
        
        return 1;
    }

    @Transactional
    @Override
    public int updateArticle(Long id, Article article,List<Tag> tgs) {
        Article article1 = articleRepository.getOne(id);
        if (article1 == null) {
            throw new NotFoundException("该文章不存在！");
        }
        BeanUtils.copyProperties(article, article1, MyBeanUtils.getNullPropertyNames(article));
        article1.setUpdateTime(new Date());
        
        Map<String,Object> map = new HashMap<String,Object>();
      	 map.put("articles_id", id);
      	 
      	tagService.deletebyaid(map);
      	 
        for(Tag tg:tgs) {
       	 Map<String,Object> map5 = new HashMap<String,Object>();
       	 map5.put("articles_id", id);
       	 map5.put("tags_id", tg.getId());
       	tagService.saveaTag(map5);
       }
        
        return articleRepository.update(article1);
    }

    @Transactional
    @Override
    public void deleteArticle(Long id) {
    	
    	Map<String,Object> map5 = new HashMap<String,Object>();
      	 map5.put("id", id);
    	
        articleRepository.remove(map5);
    }

	@Override
	public int count(Map<String, Object> params) {
		return articleRepository.count(params);
	}

	@Override
	public List<Article> listArticlebytag(Map<String, Object> map) {
		return articleRepository.listArticlebytag(map);
	}
	
	@Override
	public List<Article> listArticlebytype(Map<String, Object> map) {
		
		List<Article> list = articleRepository.listArticlebytype(map);
       
		
		return list;
	}
}
