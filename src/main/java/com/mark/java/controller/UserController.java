package com.mark.java.controller;

import com.mark.java.dataBean.delCasesListBean;
import com.mark.java.dataBean.resultBean;
import com.mark.java.dataBean.upLoadPicBean;
import com.mark.java.entity.caseInfo;
import com.mark.java.entity.tobacco;
import com.mark.java.serviceImp.AccountServiceImp;
import com.mark.java.serviceImp.CaseServiceImp;
import com.mark.java.serviceImp.DepartmentServiceImp;
import com.mark.java.serviceImp.NotificationServiceImp;
import com.mark.java.staticTool.staticToll;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vcc on 2017/3/27.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AccountServiceImp AccountService;
    @Autowired
    private DepartmentServiceImp departmentService;
    @Autowired
    private NotificationServiceImp mNotificationServiceImp;
    @Autowired
    private CaseServiceImp mCaseServiceImp;

    public CaseServiceImp getmCaseServiceImp() {
        return mCaseServiceImp;
    }

    public void setmCaseServiceImp(CaseServiceImp mCaseServiceImp) {
        this.mCaseServiceImp = mCaseServiceImp;
    }

    public NotificationServiceImp getmNotificationServiceImp() {
        return mNotificationServiceImp;
    }

    public void setmNotificationServiceImp(NotificationServiceImp mNotificationServiceImp) {
        this.mNotificationServiceImp = mNotificationServiceImp;
    }

    public AccountServiceImp getAccountService() {
        return AccountService;
    }

    public void setAccountService(AccountServiceImp accountService) {
        AccountService = accountService;
    }

    public DepartmentServiceImp getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentServiceImp departmentService) {
        this.departmentService = departmentService;
    }


    @RequestMapping("/editAccount")
    @ResponseBody
    public resultBean editAccount(@RequestParam int uid,@RequestBody Map map){
        String name=null;
        String phone=null;
        String email=null;
        Integer departmentId=null;
        String identityNum=null;
        try{
            name=(String)map.get("name");
        }
        catch (Exception e){
        name=null;
        }
        try{
            phone=(String)map.get("phone");
        }
        catch (Exception e){
            phone=null;
        }
        try{
            email=(String)map.get("email");
        }
        catch (Exception e){
            email=null;
        }
        try{
            departmentId=(Integer) map.get("department_id");
        }
        catch (Exception e){
            departmentId=null;
        }
        try{
            identityNum=(String) map.get("identityNum");
        }
        catch (Exception e){
            identityNum=null;
        }
        return AccountService.editAccountInfo(uid, name,phone,
                email,departmentId, identityNum);
    }

    @RequestMapping("/getNotifycationCategory")
    @ResponseBody
    public resultBean getNotifycationCategory(){

        return mNotificationServiceImp.getdelNotifyCategoryList( );
    }

    @RequestMapping("/upLoadCases")
    @ResponseBody
      public resultBean upLoadCases(@RequestBody upLoadPicBean upLoadPicBean){
        return mCaseServiceImp.casesUpload(upLoadPicBean);
      }
    @RequestMapping("/setNotificationReaded")
    @ResponseBody
    public resultBean setNotificationReaded(@RequestParam int uid,@RequestBody Map map){
        Integer notificationId=null;
        try{
            notificationId= (Integer)map.get("notification_id");
        }catch (Exception e){
            notificationId=null;
        }
        return mNotificationServiceImp.setNotificationReaded(uid,notificationId);
    }
    @RequestMapping("/getUsersCaseList")
    @ResponseBody
    public resultBean getUsersCaseList(@RequestParam Integer uid){

        resultBean resultBean= mCaseServiceImp.getUserCasesList(uid);
        List<caseInfo> list=null;
        if(resultBean.getData().size()!=0){
            list=(List<caseInfo>) resultBean.getData().get(0);
        }

        if(list!=null&&list.size()!=0)
        for(int i=0;i<list.size();i++){
            list.get(i).setAccount(null);
            list.get(i).setmDepartment(null);
        }
        return resultBean;
    }
    @RequestMapping("/getTobaccoListbyCaseNum")
    @ResponseBody
    public resultBean getTobaccoListbyCaseNum(@RequestBody Map map){
        String caseNum=null;
        try{
            caseNum=(String)map.get("number");
        }catch (Exception e){
            caseNum=null;
        }
        resultBean resultBean= mCaseServiceImp.getTobaccoListByCaseId(caseNum);
        List<tobacco> list=null;
        if(resultBean.getData().size()!=0){
            list=(List<tobacco>) resultBean.getData().get(0);
        }

        if(list!=null&&list.size()!=0)
            for(int i=0;i<list.size();i++){
            list.get(i).setmCase_info(null);
            }
                return resultBean;
    }
    @RequestMapping("/delCases")
    @ResponseBody
    public resultBean delCases(@RequestBody delCasesListBean delCasesListBean, @RequestParam int uid){
        return mCaseServiceImp.delCases(delCasesListBean.getCaseList(),uid);
    }
    @RequestMapping("/logincheck")
    @ResponseBody
    public resultBean logincheck(@RequestParam String from_platform,@RequestBody Map map){
        String username=null;
        String password=null;
        long currenTime=System.currentTimeMillis();
        System.out.println("current time : "+currenTime);
        try{
            username=(String)map.get("username");
        }
        catch (Exception e){
            username=null;
        }
        try{
            password=(String)map.get("password");
        }
        catch (Exception e){
            password=null;
        }
        return AccountService.loginCheck(username,password,from_platform);

    }
}
