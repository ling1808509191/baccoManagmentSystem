package com.mark.java.DAOImp;


import com.mark.java.DAO.tobaccoDao;
import com.mark.java.entity.tobacco;
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
public class TobaccoDaoImp implements tobaccoDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="tobacco";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(tobacco tobacco) {
        return (Integer) sessionFactory.getCurrentSession().save(tobacco);
    }

    public HashMap<String,Object> findTobacco(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<tobacco> result=null;
        result= query.list();
        resultMap.put("tobaccoList",result);
        return resultMap;
    }

    public List<tobacco> getTobaccosListByCaseNum(String num) {
        String hql="from "+tableName +" a where a.mCase_info.caseInfoNum = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,num);
        List<tobacco> result=null;
        result= query.list();
        return result;
    }

    public tobacco getTobaccoById(int id) {
        tobacco result=null;
        result=(tobacco)sessionFactory.getCurrentSession().get(tobacco.class,id);
        return result;
    }

    public boolean editTobacco(tobacco tobacco) {
        if (tobacco != null) {
            sessionFactory.getCurrentSession().update(tobacco);
            return true;
        }
        return false;
    }

    public boolean delTobacco(tobacco tobacco) {
        if(tobacco.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(tobacco);
            return true;

        }
        return false;
    }

    public boolean delTobacco(int id) {

        tobacco tobacco=(tobacco) sessionFactory.getCurrentSession().get(tobacco.class,id);
        if(tobacco!=null){
            sessionFactory.getCurrentSession().delete(tobacco);
            return true;
        }
        return false;

    }


}
