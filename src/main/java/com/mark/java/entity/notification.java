package com.mark.java.entity;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/19.
 */
@Entity
@Table
public class notification {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "n_id",referencedColumnName = "id")
      private notificaCategory mCategory;

    private String title;
    private String content;
    private int status;
    private Long publish_time;

    public Long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Long publish_time) {
        this.publish_time = publish_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public notificaCategory getmCategory() {
        return mCategory;
    }

    public void setmCategory(notificaCategory mCategory) {
        this.mCategory = mCategory;
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
