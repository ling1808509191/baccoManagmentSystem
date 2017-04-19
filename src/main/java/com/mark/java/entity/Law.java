package com.mark.java.entity;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/19.
 */
@Entity
@Table
public class Law {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "mLa_id",referencedColumnName = "id")
    private LawCategory mLawCategory;
    private String title;
    private String content;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LawCategory getmLawCategory() {
        return mLawCategory;
    }

    public void setmLawCategory(LawCategory mLawCategory) {
        this.mLawCategory = mLawCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
