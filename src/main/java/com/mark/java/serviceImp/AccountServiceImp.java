package com.mark.java.serviceImp;

import com.mark.java.DAOImp.DepartmentDaoImp;
import com.mark.java.DAOImp.StaffInfoDaoImp;
import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;
import com.mark.java.entity.department;
import com.mark.java.entity.staffInfo;
import com.mark.java.service.AccountService;
import com.mark.java.staticTool.staticToll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2016/10/22.
 */
@Service
@Transactional
public class AccountServiceImp implements AccountService {

    @Autowired
    private com.mark.java.DAOImp.AccountDAOImp AccountDAOImp;

    @Autowired
    private DepartmentDaoImp departmentDaoImp;

    @Autowired
    private StaffInfoDaoImp staffInfoDaoImp;




    public resultBean addAccount(String username, String password, boolean isAdmin, String name, int departmentId, String phone, String email, String identityNum) {
        Account account=null;
        staffInfo staffinfo=null;
        resultBean resultBean=new resultBean();
        try{
            account= AccountDAOImp.getAccountByName(username);
        }
        catch (MutilBeanException e){
            resultBean.setSuccess(0);
            resultBean.setMessage("this username is exited");
            return resultBean;
        }
        if(account==null){
            account=new Account();
            staffinfo=new staffInfo();
            department department=null;
            department=departmentDaoImp.getDepartmentById(departmentId);
            if(department==null){
                resultBean.setMessage("wrong department");
                resultBean.setSuccess(0);
                return  resultBean;
            }
            account.setIs_admin(isAdmin);
            account.setUsername(username);
            account.setPassword(password);
            staffinfo.setName(name);
            staffinfo.setDepartment(department);
            staffinfo.setIdentityNum(identityNum);
            staffinfo.setPhone(phone);
            staffinfo.setEmail(email);
            staffInfoDaoImp.save(staffinfo);
            account.setStaffInfo(staffinfo);
            int AccountId= AccountDAOImp.save(account);
            if(AccountId>0){
                resultBean.setSuccess(1);
                resultBean.setMessage("创建用户成功");
                resultBean.getData().add(AccountDAOImp.getAccountById(AccountId));
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("创建用户失败");
                return resultBean;
            }
        }
        else{
            resultBean.setMessage("用户名已存在");
            resultBean.setSuccess(0);
            return  resultBean;
        }
    }

    public resultBean loginCheck(String username, String password,String platform ) {
       Account account=null;
        resultBean resultBean=new resultBean();
        if(username==null||password==null||password==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("Required parameter missing");
            return resultBean;
        }
        try{
            account= AccountDAOImp.getAccountByName(username);
        }catch (MutilBeanException e){
            resultBean.setSuccess(0);
            resultBean.setMessage("where is some unknown error");
            return resultBean;
        }
        if(account==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("username is not exited");
            return resultBean;
        }
        if(account.getPassword().equals(password)){
            String token= staticToll.CreateToken(account.getUid(),account.getPassword());
       if(platform!=null&&platform.equals("app")){
           resultBean.setSuccess(1);
              account.setApp_token(token+";"+System.currentTimeMillis());
           resultBean.setMessage("login sucess");
           resultBean.getData().add(account);
       }else if(platform!=null&&platform.equals("web")){
           resultBean.setSuccess(1);
           account.setWeb_token(token+";"+System.currentTimeMillis());
           resultBean.setMessage("login sucess");
           resultBean.getData().add(account);
       }else{
           resultBean.setSuccess(0);
           resultBean.setMessage("can not get platform param");

       }
        }else{
            resultBean.setSuccess(0);
            resultBean.setMessage("Password error");
        }
        return resultBean;

    }

    public resultBean logoutCheck(int uid, String time) {
        return null;
    }

    public resultBean SearchAccountWithDepartmentId(String username, Boolean isAdmin, Integer status, String name, String phone, int departmentId) {
        List<Account> resultList =null;
        resultBean resultBean=new resultBean();
        resultList= AccountDAOImp.searchAccountbyDepartmentId(username,isAdmin,status,name,phone,departmentId);
        resultBean.setSuccess(1);
        resultBean.setMessage("search sucess");
        resultBean.getData().add(resultList);

        return resultBean;
    }

    public resultBean SearchAccount(String username, Boolean isAdmin, Integer status, String name, String phone) {
        List<Account> resultList =null;
        resultBean resultBean=new resultBean();
        resultList= AccountDAOImp.searchAccount(username,isAdmin,status,name,phone);
        resultBean.setSuccess(1);
        resultBean.setMessage("search sucess");
        resultBean.getData().add(resultList);

        return resultBean;
    }

    public resultBean getAllAccount(int pagenum, int pagesize) {
        HashMap<String,Object> resultList =null;
        resultBean resultBean=new resultBean();
        resultList= AccountDAOImp.findAccounts(pagenum,pagesize);
        resultBean.setSuccess(1);
        resultBean.setMessage("search sucess");
        resultBean.getData().add(resultList);

        return resultBean;
    }

    public resultBean getAccountById(int AccountId) {
        resultBean resultBean=new resultBean();
        Account account= AccountDAOImp.getAccountById(AccountId);
        if(account==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("not found this Account");
            return  resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("found this Account");
            resultBean.getData().add(account);
            return  resultBean;

        }
    }

    public resultBean editAccountStatus(int uid, int status) {
        Account account= AccountDAOImp.getAccountById(uid);
        resultBean resultBean=new resultBean();

        if(account==null){
        resultBean.setSuccess(0);
            resultBean.setMessage("not found this Account");
            return  resultBean;
        }
        account.setStatus(status);
        if(AccountDAOImp.editAccount(account)){
            resultBean.setSuccess(1);
            resultBean.setMessage("update Account sucess");
            return resultBean;
        }
        else{
            resultBean.setSuccess(0);
            resultBean.setMessage("update Account faild");
            return resultBean;
        }

    }

    public resultBean editAccountInfo(int uid, String name, String phone, String email, Integer departmentId, String identityNum) {
        Account account= AccountDAOImp.getAccountById(uid);
        resultBean resultBean=new resultBean();

        if(account==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("not found this Account");
            return  resultBean;
        }
        staffInfo staffInfo=account.getStaffInfo();

       if(name!=null){
           staffInfo.setName(name);
        }
        if(phone!=null){
            staffInfo.setPhone(phone);
        }
        if(email!=null){
            staffInfo.setEmail(email);
        }
        if(identityNum!=null){
            staffInfo.setIdentityNum(identityNum);
        }
        if(departmentId!=null){
            department department=departmentDaoImp.getDepartmentById(departmentId);
            if(department==null){
                resultBean.setSuccess(0);
                resultBean.setMessage("department is not found");
                return resultBean;
            }
            staffInfo.setDepartment(department);
        }

        account.setStaffInfo(staffInfo);
        if(AccountDAOImp.editAccount(account)){
            resultBean.setSuccess(1);
            resultBean.setMessage("update Account sucess");
            return resultBean;
        }
        else{
            resultBean.setSuccess(0);
            resultBean.setMessage("update Account faild");
            return resultBean;
        }
    }

    public com.mark.java.DAOImp.AccountDAOImp getAccountDAOImp() {
        return AccountDAOImp;
    }

    public void setAccountDAOImp(com.mark.java.DAOImp.AccountDAOImp accountDAOImp) {
        AccountDAOImp = accountDAOImp;
    }

    public DepartmentDaoImp getDepartmentDaoImp() {
        return departmentDaoImp;
    }

    public void setDepartmentDaoImp(DepartmentDaoImp departmentDaoImp) {
        this.departmentDaoImp = departmentDaoImp;
    }

    public StaffInfoDaoImp getStaffInfoDaoImp() {
        return staffInfoDaoImp;
    }

    public void setStaffInfoDaoImp(StaffInfoDaoImp staffInfoDaoImp) {
        this.staffInfoDaoImp = staffInfoDaoImp;
    }
}
