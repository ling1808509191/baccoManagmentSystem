package com.mark.java.DAO;

import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.entity.Account;


import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface AccountDao {
    public int save(Account account);
    public Account getAccountByName (String username)throws MutilBeanException;
    public List<Account> findAccounts(int pagenum,int pagesize);
    public List<Account> getAccountByDepartmentId(int departmentId);
    public Account getAccountById(int id);
    public  List<Account> searchAccountbyDepartmentId(String username,Boolean is_admin,Integer status,String name,String phone,Integer departmentId);
    public List<Account> searchAccount(String username,Boolean is_admin,Integer status,String name,String phone);
    public boolean editAccount(Account account);
    public boolean delAccount(Account account);
    public boolean delAccount(int id);
}
