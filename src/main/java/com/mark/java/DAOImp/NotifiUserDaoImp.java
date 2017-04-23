package com.mark.java.DAOImp;


import com.mark.java.DAO.notifyUserDao;
import com.mark.java.entity.notification;
import com.mark.java.entity.notificationUser;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mark on 4/24/15.
 */

@Component
public class NotifiUserDaoImp implements notifyUserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="notificationUser";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(notificationUser notificationUser) {
        return (Integer) sessionFactory.getCurrentSession().save(notificationUser);
    }

    public HashMap<String, Object> findNotifyUser(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<notificationUser> result=null;
        result= query.list();
        resultMap.put("resultList",result);
        return resultMap;
    }

    public notificationUser getNotifyUserById(int id) {
       notificationUser result=null;
        result=(notificationUser)sessionFactory.getCurrentSession().get(notificationUser.class,id);
        return result;
    }

    public notificationUser getNotifyUserByUserIdAndNotificationId(int uid, int notificationId) {
        String hql="from "+tableName+" a where a.mUser.uid = ? and a.mNotification.id = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,uid);
        query.setInteger(1,notificationId);
        List<notificationUser> result=null;
        result= query.list();
        if(result==null){
            return null;
        }
        return result.get(0);
    }

    public HashMap<String, Object> getUserNotifications(int uid, int pagenum, int pagesize) {
        String hql="from "+tableName+" a where a.mUser.uid = ? ";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        Countquery.setInteger(0,uid);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setInteger(0,uid);
        List<notificationUser> result=null;
        result= query.list();
        resultMap.put("notificationList",result);
        return resultMap;
    }

    public boolean editNotifyUser(notificationUser notificationUser) {
        if (notificationUser != null) {
            sessionFactory.getCurrentSession().update(notificationUser);
            return true;
        }
        return false;
    }

    public boolean delNotifyUserByUserId(int uid) {
        String hql="delete from "+tableName+" a where a.mUser.uid = ? ";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,uid);
       return query.executeUpdate()>0;


    }

    public boolean delNotifyUserByNotificationId(int notificationId) {
        String hql="delete from "+tableName+" a where a.mNotification.id = ? ";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,notificationId);
        return query.executeUpdate()>0;
    }

    public boolean delNotifyUser(notificationUser notificationUser) {
        if(notificationUser.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(notificationUser);
            return true;

        }
        return false;
    }

    public boolean delNotifyUser(int id) {

        notificationUser notificationUser=(notificationUser) sessionFactory.getCurrentSession().get(notificationUser.class,id);
        if(notificationUser!=null){
            sessionFactory.getCurrentSession().delete(notificationUser);
            return true;
        }
        return false;

    }


}
