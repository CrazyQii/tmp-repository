package com.example.studentauth.entity;

import java.util.Date;

public class PersonInfo {
    //用戶ID
    private Long userId;
    //用戶名字
    private String userName;
    //教师名字
    private String teacherName;
    //用戶郵箱
    private String Email;
    //性別
    private String gender;
    //狀態（是否有資格登錄1代表老師2代表管理員）
    private Integer enabkeStatus;
    //用戶身份標識
    private Integer userType;
    //創建時間
    private Date createTime;
    //最近操作時間
    private Date lastEditTime;
    private String UserPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnabkeStatus() {
        return enabkeStatus;
    }

    public void setEnabkeStatus(Integer enabkeStatus) {
        this.enabkeStatus = enabkeStatus;
    }

    //enableStatus
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

}
