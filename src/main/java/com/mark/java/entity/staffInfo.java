package com.mark.java.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vcc on 2017/3/17.
 */
@Entity
@Table
public class staffInfo {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = com.mark.java.entity.department.class)
    @JoinColumn(name = "d_id",referencedColumnName = "id",nullable = false,updatable = true)
    @Cascade(CascadeType.ALL)
    private department department;

    private String name;
    private String phone;
    private String email;
    private String identityNum ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public com.mark.java.entity.department getDepartment() {
        return department;
    }

    public void setDepartment(com.mark.java.entity.department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }
}
