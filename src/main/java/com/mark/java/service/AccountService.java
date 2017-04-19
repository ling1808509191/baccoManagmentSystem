package com.mark.java.service;

import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;

/**
 * Created by vcc on 2017/3/23.
 */
public interface AccountService {
    public resultBean addAccount(String username, String password, boolean isAdmin, String name, int departmentId, String phone,
                                 String email, String identityNum);
    public resultBean loginCheck(String username,String password,String platform);
    public resultBean logoutCheck(int uid,String time);
    public resultBean SearchAccountWithDepartmentId(String username,Boolean isAdmin,Integer status,String name,String phone,int departmentId);
    public resultBean SearchAccount(String username,Boolean isAdmin,Integer status,String name,String phone);
    public resultBean getAllAccount(int pagenum,int pagesize);
    public resultBean getAccountById(int AccountId);
    public resultBean editAccountStatus(int uid,int status);
    public resultBean editAccountInfo(int uid,String name,String phone,String email,Integer departmentId,String identityNum);
}
