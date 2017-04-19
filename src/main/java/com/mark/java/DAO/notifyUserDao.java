package com.mark.java.DAO;
import com.mark.java.entity.notification;
import com.mark.java.entity.notificationUser;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface notifyUserDao {
    public int save(notificationUser notifi);
    public List<notificationUser> findNotifyUser(int pagenum, int pagesize);
    public boolean editNotifyUser(notificationUser notifyUser);
    public boolean delNotifyUserByUserId(int uid);
    public boolean delNotifyUserByNotificationId(int notificationId);
    public notificationUser getNotifyUserById(int id);
    public notificationUser getNotifyUserByUserIdAndNotificationId(int uid,int notificationId);
    public List<notificationUser> getUserNotifications(int uid,int pagenum,int pagesize);
    public boolean delNotifyUser(notificationUser notifyUser);
    public boolean delNotifyUser(int id);
}
