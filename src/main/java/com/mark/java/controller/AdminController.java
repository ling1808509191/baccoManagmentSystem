package com.mark.java.controller;

import com.mark.java.dataBean.publishRequestBean;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.notification;
import com.mark.java.entity.notificationUser;
import com.mark.java.serviceImp.AccountServiceImp;
import com.mark.java.serviceImp.DepartmentServiceImp;
import com.mark.java.serviceImp.LawServiceImp;
import com.mark.java.serviceImp.NotificationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vcc on 2017/3/31.
 */
@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AccountServiceImp AccountService;
    @Autowired
    private DepartmentServiceImp departmentService;
    @Autowired
    private LawServiceImp LawServiceImp;
    @Autowired
    private NotificationServiceImp mNotificationServiceImp;

    public com.mark.java.serviceImp.LawServiceImp getLawServiceImp() {
        return LawServiceImp;
    }

    public void setLawServiceImp(com.mark.java.serviceImp.LawServiceImp lawServiceImp) {
        LawServiceImp = lawServiceImp;
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
    /*
        -----------------------------------------Account start---------------------------------------------
         */
    @RequestMapping("/addAccount")
    @ResponseBody
    public resultBean addAccount(@RequestBody Map map){
        resultBean resultBean=new resultBean();
        return AccountService.addAccount( (String)map.get("username"),   (String)map.get("password"),(Boolean)map.get("is_admin"),
                (String)map.get("name"),(Integer)map.get( "department_id"), (String)map.get("phone"),
                (String)map.get("email"), (String)map.get("identity_num"));
    }
    @RequestMapping("/searchAcount")
    @ResponseBody
    public resultBean searchAcount(@RequestBody Map map){
        Integer uid=null;
        String username=null;

        Boolean is_admin=null;

        Integer status=null;

        String name=null;

        String phone=null;

        Integer departmentId=null;
        try{
            uid= (Integer)map.get("uid");
        }catch (NullPointerException e){
            uid=null;
        }
        try{
            username= (String)map.get("username");
        }catch (NullPointerException e){
            username="";
        }finally {
            if(username==null){
                username="";
            }
        }
        try{
            is_admin=  (Boolean)map.get("is_admin");
        }catch (NullPointerException e){
            is_admin=null;
        }

        try{
            status=  (Integer) map.get("status");
        }catch (NullPointerException e){
            status=null;
        }

        try{
            name=  (String)map.get("name");
        }catch (NullPointerException e){
            name="";
        }
        finally {
            if(name==null){
                name="";
            }
        }
        try{
            phone=(String)map.get("phone");
        }catch (NullPointerException e){
            phone="";
        }
        finally {
            if(phone==null){
                phone="";
            }
        }
        try{
            departmentId= (Integer)map.get("department_id");
        }catch (NullPointerException e){
            departmentId=null;
        }

        if (uid == null) {
            if(departmentId==null){
                return AccountService.SearchAccount(username,is_admin,status,name,phone);
            }else{
                return AccountService.SearchAccountWithDepartmentId(username,is_admin,status,name,phone,departmentId);
            }
        }else{
            return AccountService.getAccountById(uid);
        }

    }
    @RequestMapping("/getAllUser")
    @ResponseBody
    public resultBean getAllUser(@RequestParam Integer pagenum,@RequestParam Integer pagesize){
        return AccountService.getAllAccount(pagenum,pagesize);

    }
    @RequestMapping("/editAccountStatus")
    @ResponseBody
    public resultBean editAccountStatus(@RequestBody Map map){

        return AccountService.editAccountStatus( (Integer) map.get("uid"),(Integer)map.get("status"));
    }
    /*
    -----------------------------------------Account End---------------------------------------------
    -----------------------------------------department start---------------------------------------------
     */
    @RequestMapping("/searchDepartment")
    @ResponseBody
    public resultBean searchDepartment(@RequestBody Map map){
        Integer deparmentId=null;
        String name=null;
        String phone=null;
        try{
            deparmentId= (Integer)map.get("department_id");
        }catch (Exception e){
            deparmentId=null;
        }

        try{
            name= (String)map.get("name");
        }catch (Exception e){
            name=null;
        }
        finally {
            if(name==null)
                name="";
        }
        try{
            phone= (String)map.get("phone");
        }catch (Exception e){
            phone=null;
        }
        finally {
            if(phone==null){
                phone="";
            }
        }
        if(deparmentId==null)
            return departmentService.searchDepartment(name,phone);
        else{
            return departmentService.getDepartmentById(deparmentId);
        }
    }

    @RequestMapping("/editDepartment")
    @ResponseBody
    public resultBean editDepartment(@RequestBody Map map){
        String name=null;
        String phone=null;
        String description=null;
        try{
             name=(String)map.get("name");
        }catch (Exception e){
            name=null;
        }
        try{
            phone=(String)map.get("phone");
        }catch (Exception e){
            phone=null;
        } try{
            description=(String)map.get("description");
        }catch (Exception e){
            description=null;
        }
        return departmentService.editDepartment( (Integer) map.get("departmnet_id"),  name ,phone,description);
    }
    @RequestMapping("/addDepartment")
    @ResponseBody
    public resultBean addDepartment(@RequestBody Map map){
        return departmentService.addDepartment((String)map.get("name"),(String)map.get("description"),(String)map.get("phone"));


    }
    @RequestMapping("/getAllDepartment")
    @ResponseBody
    public resultBean getAllDepartment(@RequestParam int pagenum,@RequestParam int pagesize){

        return departmentService.getAllDepartments(pagenum,pagesize);
    }
    /*
     ----------------------------------------department End---------------------------------------------
     -----------------------------------------law start---------------------------------------------
      */
    @RequestMapping("/addLawCategory")
    @ResponseBody
    public resultBean addLawCategory(@RequestBody Map map){
      String name=(String)map.get("name");
        return LawServiceImp.addLawCategory(name);
    }
    @RequestMapping("/delLawCategory")
    @ResponseBody
    public resultBean delLawCategory(@RequestBody Map map){
        Integer category_id=(Integer)map.get("category_id");
        return LawServiceImp.delLawCategory(category_id);
    }
    @RequestMapping("/getCategoryList")
    @ResponseBody
    public resultBean getCategoryList(){

        return LawServiceImp.getLawCategoryList();
    }
    @RequestMapping("/addLawInstrument")
    @ResponseBody
    public resultBean addLawInstrument(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");
        String title=(String) map.get("title");
        String content=(String) map.get("content");
        Integer status=(Integer)map.get("status");
        return LawServiceImp.addLegalInstrument(categoryId,title,content,status);
    }
    @RequestMapping("/editLawInstrument")
    @ResponseBody
    public resultBean editLawInstrument(@RequestBody Map map){
        Integer lawID=(Integer)map.get("law_id");
        String title=null;
        String content=null;
        Integer status=null;
        try{
            title= (String) map.get("title");
        }catch (Exception e){
            title=null;
        }
        try{
            content=(String) map.get("content");
        }catch (Exception e){
            content=null;
        }
        try{
            status= (Integer)map.get("status");
        }catch (Exception e){
            title=null;
        }

        return LawServiceImp.editLegalInstrument(lawID,title,content);
    }
    @RequestMapping("/getLawInstrumentById")
    @ResponseBody
    public resultBean getLawInstrumentById(@RequestBody Map map){
        Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.getLawById(lawId);
    }
    @RequestMapping("/delLawInstrument")
    @ResponseBody
    public resultBean delLawInstrument(@RequestBody Map map){
        Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.delLegalInstrument(lawId);
    }
    @RequestMapping("/getLawByCategoryId")
    @ResponseBody
    public resultBean getLawByCategoryId(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");

        return LawServiceImp.getOneTypeLaws(categoryId);
    }
    @RequestMapping("/getAllLawInstrument")
    @ResponseBody
    public resultBean getAllLawInstrument(@RequestParam int pagenum,@RequestParam int uid,@RequestParam int pagesize,@RequestBody Map map){

        return LawServiceImp.getAllLegalInstrument(uid,pagenum,pagesize);
    }
    @RequestMapping("/publishLaw")
    @ResponseBody
    public resultBean publishLaw(@RequestBody Map map){
    Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.publishLegal(lawId);
    }
    /*
     ----------------------------------------law End---------------------------------------------
     -----------------------------------------notication start---------------------------------------------
      */
    @RequestMapping("/addNotificationCategory")
    @ResponseBody
    public resultBean addNotificationCategory(@RequestBody Map map){
        String name=(String)map.get("name");

        return mNotificationServiceImp.addNotifyCategory(name);
    }
    @RequestMapping("/delNotificationCategory")
    @ResponseBody
    public resultBean delNotificationCategory(@RequestBody Map map){
        Integer categoryId=(Integer) map.get("category_id");

        return mNotificationServiceImp.delNotifyCategory(categoryId);
    }
    @RequestMapping("/addNotifycation")
    @ResponseBody
    public resultBean addNotifycation(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");
        String title=null;
        String content=null;
        try{
            title= (String)map.get("title");
        }catch (Exception e){
            title=null;
        }
        try{
            content =(String)map.get("content");
        }catch (Exception e){
            content=null;
        }

        return mNotificationServiceImp.addNotification(categoryId,title,content );
    }
    @RequestMapping("/editNotifycation")
    @ResponseBody
    public resultBean editNotifycation(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("notification_id");
        String title=null;
        try{
            title= (String)map.get("title");
        }catch (Exception e){
            title=null;
        }
        String content=null;
        try{
            content =(String)map.get("content");
        }catch (Exception e){
            content=null;
        }
        return mNotificationServiceImp.editNotification(categoryId,title,content );
    }
    @RequestMapping("/getNotificationById")
    @ResponseBody
    public resultBean getNotificationById(@RequestBody Map map){
        Integer notificationId=null;
        try{
            notificationId =   (Integer)map.get("notification_id");
        }catch (Exception e){
            notificationId=null;
        }
        return mNotificationServiceImp.getNotificationById(notificationId);
    }
    @RequestMapping("/publishNotification")
    @ResponseBody
    public resultBean publishNotification(@RequestBody  publishRequestBean requestBean){
        Integer notificationId=null;
        Integer departmentId=null;
        return mNotificationServiceImp.publishNotification(requestBean.getNotification_id(),requestBean.getDepartment_ids());
    }
    @RequestMapping("/delNotification")
    @ResponseBody
    public resultBean delNotification(@RequestBody Map map){
        Integer notificationId=null;
       try{
           notificationId=(Integer)map.get("notification_id");
       }catch (Exception e){
           notificationId=null;
       }

        return mNotificationServiceImp.delNotification(notificationId);
    }
    @RequestMapping("/getNotificationByCategory")
    @ResponseBody
    public resultBean getNotificationByCategory(@RequestParam int pagenum,@RequestParam int pagesize,@RequestBody Map map){
        Integer categoryId=null;
        try{
            categoryId=(Integer)map.get("category_id");
        }catch (Exception e){
            categoryId=null;
        }

        return mNotificationServiceImp.getTypeNotifications(categoryId,pagenum,pagesize);
    }
    @RequestMapping("/UserGetNotifications")
    @ResponseBody
    public resultBean UserGetNotifications(@RequestParam  int uid,@RequestParam int pagenum,@RequestParam int pagesize){

        resultBean resultBean= mNotificationServiceImp.getUserTypeNotifications(uid,pagenum,pagesize);
       if(resultBean.getSucess()==1){
           List<notificationUser> List=(List)resultBean.getData().get(0);
           if(List!=null&&List.size()!=0){
               for(int i=0;i<List.size();i++){
                   List.get(i).setmUser(null);
               }
           }
       }
       return resultBean;
    }

}
