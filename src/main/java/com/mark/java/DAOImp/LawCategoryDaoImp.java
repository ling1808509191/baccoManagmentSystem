package com.mark.java.DAOImp;

import com.mark.java.DAO.LawCategoryDao;
import com.mark.java.entity.LawCategory;
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
public class LawCategoryDaoImp implements LawCategoryDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="LawCategory";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(LawCategory LawCategory) {
        return (Integer) sessionFactory.getCurrentSession().save(LawCategory);
    }

    public HashMap<String,Object> findLawCategory(int pagenum, int pagesize) {
       String hql="from "+tableName;
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<LawCategory> result=null;
        result= query.list();
        resultMap.put("LawCategoryList",result);
        return resultMap;
    }

    public LawCategory getLawCategoryByName(String name) {
        String hql=" from "+tableName+" a where a.name = ?";
        List<LawCategory> resultList;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,name);
        resultList= query.list();
        if(resultList.size()==1)
            return resultList.get(0);
        else if(resultList.size()==0){
            return null;

        }
        return null;
    }




    public LawCategory getLawCategoryById(int id) {
       LawCategory result=null;
        result=(LawCategory)sessionFactory.getCurrentSession().get(LawCategory.class,id);
        return result;
    }

    public boolean editLawCategory(LawCategory LawCategory) {
        if (LawCategory != null) {
            sessionFactory.getCurrentSession().update(LawCategory);
            return true;
        }
        return false;
    }

    public boolean delLawCategory(LawCategory LawCategory) {
        if(LawCategory.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(LawCategory);
            return true;

        }
        return false;
    }

    public boolean delLawCategory(int id) {

        LawCategory LawCategory=(LawCategory) sessionFactory.getCurrentSession().get(LawCategory.class,id);
        if(LawCategory!=null){
            sessionFactory.getCurrentSession().delete(LawCategory);
            return true;
        }
        return false;

    }
}
