package com.felix.forum.po;

import java.util.Date;

/**
 * @program: Discuss
 * @description:
 * @author: hanLinQi
 * @create: 2022-03-04 17:20
 **/

public class Discuss {

    private Long id;
    private String content;
    private String title;
    private Date createTime;
    private Long parentId;
    private Long userId;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Discuss{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", parentId=" + parentId +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
}
