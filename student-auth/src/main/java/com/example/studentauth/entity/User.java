package com.example.studentauth.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * &#64;author cao_wencao
 * &#64;date 2018年12月13日 下午4:50:18
 * </pre>
 */
@ExcelTarget("20")
@Setter
@Getter
@ToString
public class User implements java.io.Serializable {
    @Excel(name = "id", width = 15)
    private Integer id;

    @Excel(name = "姓名", orderNum = "0", width = 30)
    private String name;

    @Excel(name = "性别", replace = {"男_1", "女_2"}, orderNum = "1", width = 30)
    private String sex;

    @Excel(name = "生日", exportFormat = "yyyy-MM-dd", orderNum = "2", width = 30)
    private String birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


}