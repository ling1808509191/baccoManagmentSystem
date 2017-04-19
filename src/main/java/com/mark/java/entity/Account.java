package com.mark.java.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/17.
 */
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue
    private int uid;
    private String username;
    private String password;
    private boolean is_admin;
    private int status;
    @OneToOne(targetEntity = com.mark.java.entity.staffInfo.class ,cascade = CascadeType.ALL)
    @JoinColumn(name="staf_id",referencedColumnName = "id",unique = true)
    private staffInfo staffInfo;

    public staffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(staffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    private String web_token;
    private String app_token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean is_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWeb_token() {
        return web_token;
    }

    public void setWeb_token(String web_token) {
        this.web_token = web_token;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }

}
