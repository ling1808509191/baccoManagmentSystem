package com.mark.java.DAO;
import com.mark.java.entity.notificaCategory;
import com.mark.java.entity.notification;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface notificationDao {
    public int save(notification notification);
    public HashMap<String,Object> findNotification(int pagenum, int pagesize);
    public  HashMap<String,Object> findNotificationByCategoryId(int categoryId,int pagenum, int pagesize);
    public notification getNotificationById(int id);
    public int getNotificationNumberByCategoryId(int categoryId);
    public boolean editNotification(notification Notification);
    public boolean delNotification(notification Notification);
    public boolean delNotification(int id);
}
