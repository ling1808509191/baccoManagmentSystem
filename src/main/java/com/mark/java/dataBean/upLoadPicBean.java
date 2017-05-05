package com.mark.java.dataBean;

import java.util.List;

/**
 * Created by vcc on 2017/4/9.
 */
public class upLoadPicBean {
    private String date;
    private Integer department_id;
    private String num;


    private Integer user_id;
    private Integer total_num;
    private List<cigarete> cigaretteList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public List<cigarete> getCigaretteList() {
        return cigaretteList;
    }

    public void setCigaretteList(List<cigarete> cigaretteList) {
        this.cigaretteList = cigaretteList;
    }
}
