package com.mark.java.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vcc on 2016/10/22.
 */

public class requestBean {
    private String user_id;
    private String time;
    private String location;
    private String date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
