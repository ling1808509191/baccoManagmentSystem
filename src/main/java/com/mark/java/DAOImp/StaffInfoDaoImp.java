package com.mark.java.DAOImp;


import com.mark.java.DAO.staffInfoDao;
import com.mark.java.DAO.staffInfoDao;
import com.mark.java.entity.notificationUser;
import com.mark.java.entity.staffInfo;
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
public class StaffInfoDaoImp implements staffInfoDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="staffInfo";


    public int save(staffInfo staffInfo) {
        return (Integer) sessionFactory.getCurrentSession().save(staffInfo);
    }

    public List<staffInfo> findStaffInfo(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<staffInfo> result=null;
        result= query.list();
        return result;
    }

    public staffInfo getStaffInfoById(int id) {
        staffInfo result=null;
        result=(staffInfo)sessionFactory.getCurrentSession().get(staffInfo.class,id);
        return result;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public staffInfo getStaffInfoByAccountId(int id) {
        return null;
    }

    public boolean editStaffInfo(staffInfo staffInfo) {
        if (staffInfo != null) {
            sessionFactory.getCurrentSession().saveOrUpdate(staffInfo);
            return true;
        }
        return false;
    }

    public boolean delStaffInfo(staffInfo staffInfo) {
        if(staffInfo.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(staffInfo);
            return true;

        }
        return false;
    }

    public boolean delStaffInfo(int id) {

        staffInfo staffInfo=(staffInfo) sessionFactory.getCurrentSession().get(staffInfo.class,id);
        if(staffInfo!=null){
            sessionFactory.getCurrentSession().delete(staffInfo);
            return true;
        }
        return false;

    }


}
