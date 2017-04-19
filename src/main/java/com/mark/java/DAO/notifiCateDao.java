package com.mark.java.DAO;
import com.mark.java.entity.notificaCategory;
import com.mark.java.entity.notification;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface notifiCateDao {
    public int save(notificaCategory notifi);
    public List<notificaCategory> findNotificaCategory(int pagenum, int pagesize);
    public List<notificaCategory> findNotificaCategory();
    public notificaCategory getNotificaCategoryById(int id);
    public notificaCategory getNotificaCategoryByName(String  name);
    public boolean editNotificaCategory(notificaCategory notificaCategory);
    public boolean delNotificaCategory(notificaCategory notificaCategory);
    public boolean delNotificaCategory(int id);
}
