package com.mark.java.serviceImp;

import com.mark.java.DAOImp.DepartmentDaoImp;
import com.mark.java.DAOImp.StaffInfoDaoImp;
import com.mark.java.DAOImp.appVersionDaoImp;
import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;
import com.mark.java.entity.appVersion;
import com.mark.java.entity.department;
import com.mark.java.entity.staffInfo;
import com.mark.java.service.AccountService;
import com.mark.java.service.appVersionService;
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
public class AppVersionServiceImp implements appVersionService {
    @Autowired
    private com.mark.java.DAOImp.appVersionDaoImp appVersionDaoImp;

    public com.mark.java.DAOImp.appVersionDaoImp getAppVersionDaoImp() {
        return appVersionDaoImp;
    }

    public void setAppVersionDaoImp(com.mark.java.DAOImp.appVersionDaoImp appVersionDaoImp) {
        this.appVersionDaoImp = appVersionDaoImp;
    }

    public resultBean saveApp(String filename, String description, String version) {
        resultBean resultBean=new resultBean();
        appVersion tempAppVersion=appVersionDaoImp.getAppVersionByVersion(version);
        if(tempAppVersion!=null){
            resultBean.setSuccess(0);
            resultBean.setMessage("相同版本已经存在");
            return resultBean;
        }else{
            tempAppVersion=new appVersion();
            tempAppVersion.setApkUrl(filename);
            tempAppVersion.setDescription(description);
            tempAppVersion.setUploadTime(System.currentTimeMillis());
            tempAppVersion.setVersion(version);
            int appVersionId=appVersionDaoImp.save(tempAppVersion);
            if(appVersionId>0){
                resultBean.setMessage("save apk success");
                resultBean.setSuccess(1);
                resultBean.getData().add(appVersionDaoImp.getAppVersionById(appVersionId));
                return resultBean;
            }else{
                resultBean.setSuccess(0);
                resultBean.setMessage("save apk faild");
                return resultBean;
            }

        }
    }

    public resultBean getAllAppVersion(int pagenum,int pagesize) {
        resultBean resultBean=new resultBean();
        HashMap<String,Object> AllAppVersion=appVersionDaoImp.findappVersion(pagenum,pagesize);
        if(AllAppVersion==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("获取所有apk记录失败");
            return resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("获取所有apk记录成功");
            resultBean.getData().add(AllAppVersion);
            return resultBean;
        }
    }

    public resultBean getNewestAppVersion() {
        resultBean resultBean=new resultBean();
        appVersion tempAppVersion=appVersionDaoImp.getNewestAppVersion();
        if(tempAppVersion==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("获取最新apk失败");
            return resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("获取最新apk成功");
            resultBean.getData().add(tempAppVersion);
            return resultBean;
        }
    }
}
