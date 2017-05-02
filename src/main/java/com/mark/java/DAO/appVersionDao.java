package com.mark.java.DAO;


import com.mark.java.entity.Account;
import com.mark.java.entity.Result;
import com.mark.java.entity.appVersion;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface appVersionDao {
    public int save(appVersion account);
    public List<appVersion> findappVersion(int pagenum, int pagesize);
    public appVersion getAppVersionById(int id);
    public appVersion getAppVersionByVersion(String version);
    public appVersion getNewestAppVersion();
    public boolean editappVersion(appVersion appVersion);
    public boolean delappVersion(appVersion appVersion);
    public boolean delappVersion(int id);
}
