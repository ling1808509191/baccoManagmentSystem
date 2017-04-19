package com.mark.java.service;

import com.mark.java.dataBean.resultBean;

import java.util.List;

/**
 * Created by vcc on 2017/3/23.
 */
public interface NotificationService {
    public resultBean addNotifyCategory(String NotifyCategory);

    public resultBean delNotifyCategory(int categoryId);

    public resultBean getdelNotifyCategoryList();

    public resultBean addNotification(int categoryId, String title, String content);

    public resultBean editNotification(int NotifiCationId, String title, String content, int status);

    public resultBean delNotification(Integer NotifiCationId);

    public resultBean editNotification(int NotifiCationId, String title, String content);

    public resultBean publishNotification(int NotificationId, List<Integer> departmentList);

    public resultBean getNotificationById(int NotificationId);

    public resultBean getNotificationUserByUserAndNotification(int uid,int notificationId);

    public resultBean getTypeNotifications(int NotifyCategoryId,int pagenum,int pagesize);

    public resultBean setNotificationReaded(int uid,int notificationId);

    public resultBean getUserTypeNotifications(int uid,int pagenum,int pagesize);

    public resultBean eidtNotificationStatus(int uid,int notificationId);
}
