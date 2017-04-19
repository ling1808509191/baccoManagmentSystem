package com.mark.java.DAOImp;

import com.mark.java.DAO.appVersionDao;
import com.mark.java.entity.Account;
import com.mark.java.entity.appVersion;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vcc on 2017/3/22.
 */
@Component
public class appVersionDaoImp implements appVersionDao {
    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="appVersion";

    public int save(appVersion appVersion) {
        return (Integer) sessionFactory.getCurrentSession().save(appVersion);
    }

    public List<appVersion> findappVersion(int pagenum, int pagesize) {
        String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<appVersion> result=null;
        result= query.list();
        return result;
    }

    public appVersion getAppVersionById(int id) {
        appVersion result=null;
        result=(appVersion)sessionFactory.getCurrentSession().get(appVersion.class,id);
        return result;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean editappVersion(appVersion appVersion) {
        if (appVersion != null) {
            sessionFactory.getCurrentSession().update(appVersion);
            return true;
        }
        return false;
    }

    public boolean delappVersion(appVersion appVersion) {
        if(appVersion.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(appVersion);
            return true;

        }
        return false;
    }

    public boolean delappVersion(int id) {
        appVersion appVersion=(appVersion) sessionFactory.getCurrentSession().get(appVersion.class,id);
        if(appVersion!=null){
            sessionFactory.getCurrentSession().delete(appVersion);
            return true;
        }
        return false;

    }

}
