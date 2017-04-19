package com.mark.java.DAO;
import com.mark.java.entity.notificaCategory;
import com.mark.java.entity.notification;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface notificationDao {
    public int save(notification notification);
    public List<notification> findNotification(int pagenum, int pagesize);
    public List<notification> findNotificationByCategoryId(int categoryId,int pagenum, int pagesize);
    public notification getNotificationById(int id);
    public boolean editNotification(notification Notification);
    public boolean delNotification(notification Notification);
    public boolean delNotification(int id);
}
