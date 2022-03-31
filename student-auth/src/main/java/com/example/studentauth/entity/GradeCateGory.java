package com.example.studentauth.entity;

import java.util.Date;

public class GradeCateGory {
    private Long gradeCategoryId;
    private String gradeCategoryName;
    private String gradeCategoryDesc;
    private String gradeCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private GradeCateGory parent;

    public Long getGradeCategoryId() {
        return gradeCategoryId;
    }

    public void setGradeCategoryId(Long gradeCategoryId) {
        this.gradeCategoryId = gradeCategoryId;
    }

    public String getGradeCategoryName() {
        return gradeCategoryName;
    }

    public void setGradeCategoryName(String gradeCategoryName) {
        this.gradeCategoryName = gradeCategoryName;
    }

    public String getGradeCategoryDesc() {
        return gradeCategoryDesc;
    }

    public void setGradeCategoryDesc(String gradeCategoryDesc) {
        this.gradeCategoryDesc = gradeCategoryDesc;
    }

    public String getGradeCategoryImg() {
        return gradeCategoryImg;
    }

    public void setGradeCategoryImg(String gradeCategoryImg) {
        this.gradeCategoryImg = gradeCategoryImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public GradeCateGory getParent() {
        return parent;
    }

    public void setParent(GradeCateGory parent) {
        this.parent = parent;
    }
}
