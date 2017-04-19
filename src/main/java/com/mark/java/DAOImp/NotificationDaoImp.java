package com.mark.java.DAOImp;

import com.mark.java.DAO.notifiCateDao;
import com.mark.java.DAO.notificationDao;
import com.mark.java.entity.notification;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mark on 4/24/15.
 */

@Component
public class NotificationDaoImp implements notificationDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="notification";


    public int save(notification notification) {
        return (Integer) sessionFactory.getCurrentSession().save(notification);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<notification> findNotification(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<notification> result=null;
        result= query.list();
        return result;
    }
//    ALTER TABLE caseInfo
//    ADD CONSTRAINT `FK_DepartmentReference_2`
//    FOREIGN KEY (`d_id` )
//    REFERENCES department (`id` )
//    ON DELETE CASCADE
//    ON UPDATE RESTRICT;
//    ALTER TABLE caseInfo DROP FOREIGN KEY `FK_oxk7bnk7ln5dnpwpb8vrl7o3a` ;
    public List<notification> findNotificationByCategoryId(int categoryId, int pagenum, int pagesize) {
        String hql=" from "+tableName+" a where a.mCategory.id = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,categoryId);
        query.setFirstResult((pagenum-1)*pagesize);
        return query.list();
    }

    public notification getNotificationById(int id) {
       notification result=null;
        result=(notification)sessionFactory.getCurrentSession().get(notification.class,id);
        return result;
    }


    public boolean editNotification(notification notification) {
        if (notification != null) {
            sessionFactory.getCurrentSession().update(notification);
            return true;
        }
        return false;
    }

    public boolean delNotification(notification notification) {
        if(notification.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(notification);
            return true;

        }
        return false;
    }

    public boolean delNotification(int id) {

        notification notification=(notification) sessionFactory.getCurrentSession().get(notification.class,id);
        if(notification!=null){
            sessionFactory.getCurrentSession().delete(notification);
            return true;
        }
        return false;

    }


}
