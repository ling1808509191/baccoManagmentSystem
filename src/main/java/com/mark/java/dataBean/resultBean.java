package com.mark.java.dataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vcc on 2017/3/24.
 */
public class resultBean {
    private int sucess;
    private String message;
    private List<Object> data;
    public resultBean(){
        this.sucess=-1;
        this.message="";
        data=new ArrayList<Object>();

    }
    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
