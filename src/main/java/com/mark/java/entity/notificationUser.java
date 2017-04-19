package com.mark.java.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vcc on 2017/3/19.
 */
@Entity
@Table
public class notificationUser implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = notification.class)
    @JoinColumn(name = "mNoid",referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    private notification mNotification;

    @JoinColumn(name = "m_id",referencedColumnName = "uid")
    @ManyToOne(targetEntity = Account.class)
    private   Account mUser;
    private long publish_time;
    private boolean readed;

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public notification getmNotification() {
        return mNotification;
    }

    public void setmNotification(notification mNotification) {
        this.mNotification = mNotification;
    }

    public Account getmUser() {
        return mUser;
    }

    public void setmUser(Account mUser) {
        this.mUser = mUser;
    }

    public boolean isRead() {
        return readed;
    }

    public void setRead(boolean read) {
        this.readed = read;
    }
}
