package com.mark.java.DAOImp;

import com.mark.java.DAO.caseInfoDao;
import com.mark.java.entity.caseInfo;
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
public class CaseInfoDAOImp implements caseInfoDao {
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="caseInfo";


    public int save(caseInfo caseInfo) {
        return (Integer) sessionFactory.getCurrentSession().save(caseInfo);
    }



    public List<caseInfo> findCaseInfo(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<caseInfo> result=null;
        result= query.list();
        return result;
    }

    public caseInfo getCaseInfoById(int id) {
       caseInfo result=null;
        result=(caseInfo)sessionFactory.getCurrentSession().get(caseInfo.class,id);
        return result;
    }

    public caseInfo getCaseInfoByCaseNum(String CaseNum) {
        String hql="from "+tableName+" a where a.caseInfoNum = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,CaseNum);
        List<caseInfo> result=null;
        result= query.list();
        if(result==null||result.size()==0){
            return null;
        }else{
            return result.get(0);
        }
    }

    public boolean editCaseInfo(caseInfo caseInfo) {
        if (caseInfo != null) {
            sessionFactory.getCurrentSession().update(caseInfo);
            return true;
        }
        return false;
    }

    public List<caseInfo>  getUserCaseInfos(int uid) {
        String hql="from "+tableName+" a where a.Account.uid = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,uid);
        List<caseInfo> result=null;
       return  query.list();
    }


    public boolean delCaseInfo(caseInfo caseInfo) {
        if(caseInfo.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(caseInfo);
            return true;

        }
        return false;
    }

    public boolean delCaseInfoByCaseNum(String caseInfoNum) {
        String hql="delete from "+tableName+" a where a.caseInfoNum = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,caseInfoNum);
       try{
           query.executeUpdate();
           }catch (Exception e){
               return false;
       }
       return true;
    }

    public boolean delCaseInfo(int id) {

        caseInfo caseInfo=(caseInfo) sessionFactory.getCurrentSession().get(caseInfo.class,id);
        if(caseInfo!=null){
            sessionFactory.getCurrentSession().delete(caseInfo);
            return true;
        }
        return false;

    }
}
