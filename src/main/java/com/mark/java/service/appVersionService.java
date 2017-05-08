package com.mark.java.service;

import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;
import com.mark.java.entity.appVersion;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface appVersionService {
    public resultBean saveApp(String filename,String description,String version);
    public resultBean getAllAppVersion(int pagenum,int pagesize);
    public resultBean getNewestAppVersion();
}
