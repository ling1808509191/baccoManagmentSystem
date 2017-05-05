package com.mark.java.entity;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/17.
 */
@Entity
@Table
public class caseInfo {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(targetEntity = com.mark.java.entity.department.class)
    @JoinColumn(name = "d_id",referencedColumnName = "id")
      private department department;

    @ManyToOne(targetEntity = com.mark.java.entity.Account.class)
    @JoinColumn(name = "A_id",referencedColumnName = "uid")
     private Account Account;

    private String submit_time;
    @Column(unique = true)
    private String caseInfoNum;
   private Long TimeStamp;

    public Long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getCaseInfoNum() {
        return caseInfoNum;
    }

    public void setCaseInfoNum(String caseInfoNum) {
        this.caseInfoNum = caseInfoNum;
    }

    public com.mark.java.entity.department getDepartment() {
        return department;
    }

    public void setDepartment(com.mark.java.entity.department department) {
        this.department = department;
    }

    private int year;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public department getmDepartment() {
        return department;
    }

    public void setmDepartment(department mDepartment) {
        this.department = mDepartment;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account mAccount) {
        this.Account = mAccount;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
