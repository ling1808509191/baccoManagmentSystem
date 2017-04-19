package com.mark.java.DAOImp;

import com.mark.java.DAO.AccountDao;
import com.mark.java.DAO.UserDAO;
import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.entity.Account;
import com.mark.java.entity.User;
import com.mark.java.entity.department;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by mark on 4/24/15.
 */

@Component
public class AccountDAOImp implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;
    private String tableName="Account";

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

    public int save(Account account) {
        return (Integer) sessionFactory.getCurrentSession().save(account);
    }

    public Account getAccountByName(String username) throws MutilBeanException {
       String hql=" from "+tableName  +" a where a.username = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,username);
        List<Account> resultList=query.list();
        if(resultList==null||resultList.size()==0)
            return null;
        if(resultList.size()==1)
            return resultList.get(0);
        if(resultList.size()>=2){
            throw new MutilBeanException("Account");
        };
        return null;
    }

    public List<Account> findAccounts(int pagenum, int pagesize) {
       String hql="from "+tableName;
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<Account> result=null;
        result= query.list();
        return result;
    }

    public List<Account> getAccountByDepartmentId(int departmentId) {
        String hql=" from "+tableName  +" a where a.staffInfo.department.id = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,departmentId);
        List<Account> resultList=query.list();
        return resultList;
    }

    public Account getAccountById(int id) {
       Account result=null;
        result=(Account)sessionFactory.getCurrentSession().get(Account.class,id);
        return result;
    }

    public List<Account> searchAccountbyDepartmentId(String username, Boolean is_admin, Integer status, String name, String phone, Integer departmentId) {
        String is_adminString=null;
        String statusString=null;
        if(is_admin==null){
            is_adminString="";
        }else{
            is_adminString=String.valueOf(is_admin);
        }
        if(status==null){
            statusString="";
        }else{
            statusString=String.valueOf(status);
        }
        String hql="from "+tableName+" a where a.username like '%"+username+"%' and a.staffInfo.name like '%"+name+
                "%' and a.staffInfo.phone like '%"+
                phone+"%' and a.is_admin like '%"+is_adminString+"%' and a.status like '%"+statusString+"%' and a.staffInfo.department.id = ?";
        System.out.println(" hql : "+hql);
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,departmentId);
        List<Account> result=query.list();
        return result;
    }



    public List<Account> searchAccount(String username, Boolean is_admin, Integer status, String name, String phone) {
        String is_adminString=null;
        String statusString=null;
        if(is_admin==null){
            is_adminString="";
        }else{
            is_adminString=String.valueOf(is_admin);
        }
        if(status==null){
            statusString="";
        }else{
            statusString=String.valueOf(status);
        }
        String hql="from "+tableName+" a where a.username like '%"+username+"%' and a.staffInfo.name like '%"+name+
                "%' and a.staffInfo.phone like '%"+
                phone+"%' and a.is_admin like '%"+is_adminString+"%' and a.status like '%"+statusString+"%'";
//
        System.out.println(" hql : "+hql);
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        List<Account> result=query.list();
        return result;
    }

    public boolean editAccount(Account account) {
        if (account != null) {
            sessionFactory.getCurrentSession().saveOrUpdate(account);
            return true;
        }
        return false;
    }

    public boolean delAccount(Account account) {
        if(account.getUid()>0)
        {
            sessionFactory.getCurrentSession().delete(account);
            return true;

        }
        return false;
    }

    public boolean delAccount(int id) {

        Account account=(Account) sessionFactory.getCurrentSession().get(Account.class,id);
        if(account!=null){
            sessionFactory.getCurrentSession().delete(account);
            return true;
        }
        return false;

    }
}
