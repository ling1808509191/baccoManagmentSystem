package com.mark.java.serviceImp;

import com.mark.java.DAOImp.*;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.*;
import com.mark.java.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/4/1.
 */
@Service
@Transactional
public class NotificationServiceImp implements NotificationService {
    @Autowired
    private NotifiCateDaoImp mNotifiCateDaoImp;
    @Autowired
    private NotificationDaoImp mNotificationDaoImp;
    @Autowired
    private NotifiUserDaoImp mNotifiUserDaoImp;
    @Autowired
    private AccountDAOImp mAccountDAOImp;
    @Autowired
    private DepartmentDaoImp departmentDaoImp;

    public DepartmentDaoImp getDepartmentDaoImp() {
        return departmentDaoImp;
    }

    public void setDepartmentDaoImp(DepartmentDaoImp departmentDaoImp) {
        this.departmentDaoImp = departmentDaoImp;
    }

    public AccountDAOImp getmAccountDAOImp() {
        return mAccountDAOImp;
    }

    public void setmAccountDAOImp(AccountDAOImp mAccountDAOImp) {
        this.mAccountDAOImp = mAccountDAOImp;
    }

    public NotifiCateDaoImp getmNotifiCateDaoImp() {
        return mNotifiCateDaoImp;
    }

    public void setmNotifiCateDaoImp(NotifiCateDaoImp mNotifiCateDaoImp) {
        this.mNotifiCateDaoImp = mNotifiCateDaoImp;
    }

    public NotificationDaoImp getmNotificationDaoImp() {
        return mNotificationDaoImp;
    }

    public void setmNotificationDaoImp(NotificationDaoImp mNotificationDaoImp) {
        this.mNotificationDaoImp = mNotificationDaoImp;
    }

    public NotifiUserDaoImp getmNotifiUserDaoImp() {
        return mNotifiUserDaoImp;
    }

    public void setmNotifiUserDaoImp(NotifiUserDaoImp mNotifiUserDaoImp) {
        this.mNotifiUserDaoImp = mNotifiUserDaoImp;
    }

    public resultBean addNotifyCategory(String NotifyCategoryName) {
        notificaCategory notifyCategory=mNotifiCateDaoImp.getNotificaCategoryByName(NotifyCategoryName);
        resultBean resultBean=new resultBean();
        if(notifyCategory!=null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification category is already exited");
            return resultBean;
        }else{
            notifyCategory=new notificaCategory();
            notifyCategory.setName(NotifyCategoryName);
            int notifycationCategoryId=mNotifiCateDaoImp.save(notifyCategory);
            if(notifycationCategoryId>0){
                resultBean.setSuccess(1);
                resultBean.setMessage("add notification category sucess");
                resultBean.getData().add(mNotifiCateDaoImp.getNotificaCategoryById(notifycationCategoryId));
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("add notification category faild");
                return resultBean;
            }
        }
    }

    public resultBean delNotifyCategory(int categoryId) {
        notificaCategory notifyCategory=mNotifiCateDaoImp.getNotificaCategoryById(categoryId);
        resultBean resultBean=new resultBean();
        if(notifyCategory==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification category is not found");
            return resultBean;
        }else{
            //List<notification> notifications=mNotificationDaoImp.findNotificationByCategoryId();
            if(mNotifiCateDaoImp.delNotificaCategory(categoryId)){
                resultBean.setSuccess(1);
                resultBean.setMessage("delete Notification category sucess");
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("delete Notification category faild");
                return resultBean;
            }
        }
    }

    public resultBean getdelNotifyCategoryList() {
        resultBean resultBean=new resultBean();
        resultBean.setSuccess(1);
        List<HashMap> result=new ArrayList<HashMap>();
        List<notificaCategory> resultList=mNotifiCateDaoImp.findNotificaCategory();
        for(int i=0;i<resultList.size();i++){
            HashMap<String,Object> temHashMap=new HashMap<String, Object>();
            temHashMap.put("Category",resultList.get(i));
            temHashMap.put("number",mNotificationDaoImp.getNotificationNumberByCategoryId(resultList.get(i).getId()));
            result.add(temHashMap);
        }
        resultBean.getData().add(result);
        return resultBean;
    }



    public resultBean addNotification(int categoryId, String title, String content) {
        notificaCategory notifyCategory=mNotifiCateDaoImp.getNotificaCategoryById(categoryId);
        resultBean resultBean=new resultBean();
        if(notifyCategory==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification category is not found");
            return resultBean;
        }else{
            notification notification=new notification();
            notification.setmCategory(notifyCategory);
            notification.setStatus(0);
            notification.setContent(content);
            notification.setTitle(title);
           int notificationId= mNotificationDaoImp.save(notification);
            if(notificationId>0){
                resultBean.setSuccess(1);
                resultBean.setMessage(" save notification sucess");
                resultBean.getData().add(mNotificationDaoImp.getNotificationById(notificationId));
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("cannot save notification");
                return resultBean;
            }
        }
    }

    public resultBean editNotification(int NotifiCationId, String title, String content, int status) {
        notification notification=mNotificationDaoImp.getNotificationById(NotifiCationId);
        resultBean resultBean=new resultBean();
        if(notification==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification  is not found");
            return resultBean;
        }else{
            if(title!=null){
                notification.setTitle(title);

            }
            if(content!=null){
                notification.setContent(content);

            }
            if(status!=-1){
                notification.setStatus(status);
            }
            if(mNotificationDaoImp.editNotification(notification)){
                resultBean.setSuccess(1);
                resultBean.setMessage("update notification sucess");
                return  resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("update notification faild");
                return  resultBean;
            }
        }
    }

    public resultBean delNotification(Integer NotifiCationId) {
        resultBean resultBean=new resultBean();
        if(NotifiCationId==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("notification id cannot be null");
            return resultBean;
        }else{
            if(mNotificationDaoImp.delNotification(NotifiCationId)){
                resultBean.setSuccess(1);
                resultBean.setMessage("del notification sucess");
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("del notification faild");
                return resultBean;
            }
        }
    }

    public resultBean editNotification(int NotifiCationId, String title, String content) {
        notification notification=mNotificationDaoImp.getNotificationById(NotifiCationId);
        resultBean resultBean=new resultBean();
        if(notification==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification  is not found");
            return resultBean;
        }else{
            if(title!=null){
                notification.setTitle(title);
            }
            if(content!=null){
                notification.setContent(content);
            }
            if(mNotificationDaoImp.editNotification(notification)){
                resultBean.setSuccess(1);
                resultBean.setMessage("edit Notification sucess");
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("edit Notification faild");
                return resultBean;
            }
        }
    }

    public resultBean publishNotification(int NotificationId, List<Integer> departmentIdList) {
        notification notification=mNotificationDaoImp.getNotificationById(NotificationId);
        resultBean resultBean=new resultBean();
        if(notification==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification  is not found");
            return resultBean;
        }else{
            notification.setStatus(1);
            notification.setPublish_time(System.currentTimeMillis());
            if(mNotificationDaoImp.editNotification(notification)){
                boolean flag=true;
                boolean departmentSizeZero=false;
                List<department> departmentList=departmentDaoImp.findDepartment();
                if(departmentIdList.size()==0){
                    departmentSizeZero=true;

                }
                for(int j=0;j<departmentIdList.size();j++){
                    List<Account> Accounts=null;
               if(departmentSizeZero) {
                   Accounts =mAccountDAOImp.getAccountByDepartmentId(departmentList.get(j).getId());
               }else{
                   Accounts =mAccountDAOImp.getAccountByDepartmentId(departmentIdList.get(j));
               }
                for(int i=0;i<Accounts.size();i++){
                    notificationUser temNotificationUser=new notificationUser();
                    temNotificationUser.setmNotification(notification);
                    temNotificationUser.setmUser(Accounts.get(i));
                    temNotificationUser.setRead(false);
                    temNotificationUser.setPublish_time(System.currentTimeMillis());

                    if(!(mNotifiUserDaoImp.save(temNotificationUser)>0)){
                        flag=false;
                        break;
                    }

                }
                }
                if(flag){
                    resultBean.setSuccess(1);
                    resultBean.setMessage("publish notification sucess");
                    return resultBean;
                }else{
                    resultBean.setSuccess(0);
                    resultBean.setMessage("publish notification faild");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }

            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("publish notification faild");
                return resultBean;
            }
        }
    }

    public resultBean getNotificationById(int NotificationId) {
        notification notification=mNotificationDaoImp.getNotificationById(NotificationId);
        resultBean resultBean=new resultBean();
        if(notification==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this Notification  is not found");
            return resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("this Notification  is  found");
            resultBean.getData().add(notification);
            return resultBean;
        }
    }

    public resultBean getNotificationUserByUserAndNotification(int uid, int notificationId) {
        return null;
    }

    public resultBean getTypeNotifications(int NotifyCategoryId, int pagenum, int pagesize) {
        HashMap<String,Object> notificationList=mNotificationDaoImp.findNotificationByCategoryId(NotifyCategoryId,pagenum,pagesize);
        resultBean resultBean=new resultBean();
        resultBean.setSuccess(1);
            resultBean.setMessage("this search notification by categoryId sucess ");
            resultBean.getData().add(notificationList);
            return resultBean;


    }

    public resultBean setNotificationReaded(int uid,int notificationId) {
        notificationUser notificationUser=mNotifiUserDaoImp.getNotifyUserByUserIdAndNotificationId(uid,notificationId);
        resultBean resultBean=new resultBean();
        if(notificationUser==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("this notificationUser  is not found");
            return resultBean;
        }else{
            notificationUser.setRead(true);
            if(mNotifiUserDaoImp.editNotifyUser(notificationUser)){
                resultBean.setSuccess(1);
                resultBean.setMessage("set notification readed sucess");

                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("set notification readed faild");
                return resultBean;
            }

        }
    }

    public resultBean getUserTypeNotifications(int uid, int pagenum, int pagesize) {
        resultBean resultBean=new resultBean();
        HashMap<String,Object> resultList=mNotifiUserDaoImp.getUserNotifications(uid,pagenum,pagesize);
        if(resultList==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("get notifications by userid faild");
            return resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("get notification by userid sucess");
            resultBean.getData().add(resultList);
            return resultBean;
        }
    }

    public resultBean eidtNotificationStatus(int uid,int notificationId) {
        notificationUser notificationUser=mNotifiUserDaoImp.getNotifyUserByUserIdAndNotificationId(uid,notificationId);
        resultBean resultBean=new resultBean();
        if(notificationUser==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("get notification faild");
            return resultBean;
        }else{
            notificationUser.setRead(true);
            if(  mNotifiUserDaoImp.editNotifyUser(notificationUser)){
                resultBean.setSuccess(1);
                resultBean.setMessage("set notification readed sucess");
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("set notificaiton readed faild");
                return resultBean;
            }
        }
    }
}
