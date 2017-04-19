package com.mark.java.DAOImp;

import com.mark.java.DAO.LawDao;
import com.mark.java.DAO.LawDao;
import com.mark.java.entity.Law;
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
public class LawDaoImp implements LawDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="Law";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(Law Law) {
        return (Integer) sessionFactory.getCurrentSession().save(Law);
    }

    public List<Law> findLaw(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<Law> result=null;
        result= query.list();
        return result;
    }

    public Law getLawById(int id) {
       Law result=null;
        result=(Law)sessionFactory.getCurrentSession().get(Law.class,id);
        return result;
    }

    public boolean editLaw(Law Law) {
        if (Law != null) {
            sessionFactory.getCurrentSession().update(Law);
            return true;
        }
        return false;
    }

    public List<Law> getPublishedLaw(int pagenum, int pagesize) {
        String hql=" from "+tableName+" a where a.status = ? ";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,1);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        return  query.list();
    }

    public List<Law> getLawByCategoryId(int categoryId) {
        String hql="from "+tableName+" a where a.mLawCategory.id = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,categoryId);
        return query.list();
    }

    public boolean delLaw(Law Law) {
        if(Law.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(Law);
            return true;

        }
        return false;
    }

    public boolean delLaw(int id) {

        Law Law=(Law) sessionFactory.getCurrentSession().get(Law.class,id);
        if(Law!=null){
            sessionFactory.getCurrentSession().delete(Law);
            return true;
        }
        return false;

    }
}
