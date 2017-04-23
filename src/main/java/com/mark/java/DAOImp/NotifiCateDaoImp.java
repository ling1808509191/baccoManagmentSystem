package com.mark.java.DAOImp;

import com.mark.java.DAO.notifiCateDao;
import com.mark.java.entity.notificaCategory;
import com.mark.java.entity.notification;
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
public class NotifiCateDaoImp implements notifiCateDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="notificaCategory";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(notificaCategory notificaCategory) {
        return (Integer) sessionFactory.getCurrentSession().save(notificaCategory);
    }

    public HashMap<String, Object> findNotificaCategory(int pagenum, int pagesize) {
       String hql="from "+tableName;
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        resultMap.put("totalNum",((Integer)query.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<notificaCategory> result=null;
        result= query.list();
        resultMap.put("categoryList",result);
        return resultMap;
    }

    public List<notificaCategory> findNotificaCategory() {
        String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        List<notificaCategory> result=null;
        result= query.list();
        return result;
    }

    public notificaCategory getNotificaCategoryById(int id) {
       notificaCategory result=null;
        result=(notificaCategory)sessionFactory.getCurrentSession().get(notificaCategory.class,id);
        return result;
    }

    public notificaCategory getNotificaCategoryByName(String name) {
       String hql=" from "+tableName+" a where a.name = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,name);
        List<notificaCategory> resultList= query.list();
        if(resultList==null||resultList.size()==0){
            return null;
        }else{
            return resultList.get(0);
        }
    }

    public boolean editNotificaCategory(notificaCategory notificaCategory) {
        if (notificaCategory != null) {
            sessionFactory.getCurrentSession().update(notificaCategory);
            return true;
        }
        return false;
    }

    public boolean delNotificaCategory(notificaCategory notificaCategory) {
        if(notificaCategory.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(notificaCategory);
            return true;

        }
        return false;
    }

    public boolean delNotificaCategory(int id) {

        notificaCategory notificaCategory=(notificaCategory) sessionFactory.getCurrentSession().get(notificaCategory.class,id);
        if(notificaCategory!=null){
            sessionFactory.getCurrentSession().delete(notificaCategory);
            return true;
        }
        return false;

    }


}
