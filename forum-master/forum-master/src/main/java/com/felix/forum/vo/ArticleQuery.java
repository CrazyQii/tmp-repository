package com.felix.forum.vo;

/**
 * @author Administrator
 * @date 2022/02/18 14:45
 **/
public class ArticleQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

    public ArticleQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
