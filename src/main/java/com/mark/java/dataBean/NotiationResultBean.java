package com.mark.java.dataBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/4/25.
 */
public class NotiationResultBean {
    private int success;
    private String message;
    private HashMap<String,Object> data;

    public NotiationResultBean() {
        this.success=-1;
        this.message="";
        this.data=new HashMap<String, Object>();
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
