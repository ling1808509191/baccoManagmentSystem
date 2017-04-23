package com.mark.java.DAOImp;

import com.mark.java.DAO.departmentDao;
import com.mark.java.DAO.departmentDao;
import com.mark.java.entity.department;
import com.sun.corba.se.spi.ior.ObjectKey;
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
public class DepartmentDaoImp implements departmentDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="department";

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int save(department department) {
        return (Integer) sessionFactory.getCurrentSession().save(department);
    }

    public HashMap<String,Object> findDepartment(int pagenum, int pagesize) {
       String hql="from "+tableName;
        HashMap<String,Object> resultMap =new HashMap<String, Object>();
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<department> result=null;
        result= query.list();
        resultMap.put("departmentList",result);
        return resultMap;
    }

    public List<department> findDepartment() {
        String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        List<department> result=query.list();
        return result;
    }

    public int getDepartmentTotalNum() {
        return 0;
    }

    public department getDepartmentById(int id) {
       department result=null;
        result=(department)sessionFactory.getCurrentSession().get(department.class,id);
        return result;
    }

    public department getDepartmentByName(String name) {
        String hql="from "+tableName+" d where d.name = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,name);
        List<department> result=query.list();
        if(result==null||result.size()==0){
            return null;
        }else{
            return result.get(0);
        }
    }

    public List<department> searchDepartment(String name, String phone) {
        String hql="from "+tableName+" d where d.name like '%"+name+"%' and d.phone like '%"+phone+"%'";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        List<department> result=query.list();
        return result;
    }

    public boolean editDepartment(department department) {
        if (department != null) {
            sessionFactory.getCurrentSession().update(department);
            return true;
        }
        return false;
    }

    public boolean delDepartment(department department) {
        if(department.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(department);
            return true;

        }
        return false;
    }

    public boolean delDepartment(int id) {

        department department=(department) sessionFactory.getCurrentSession().get(department.class,id);
        if(department!=null){
            sessionFactory.getCurrentSession().delete(department);
            return true;
        }
        return false;

    }
}
