package com.mark.java.DAO;
import com.mark.java.entity.notificationUser;
import com.mark.java.entity.staffInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface staffInfoDao {
    public int save(staffInfo staffInfo);
    public HashMap<String,Object> findStaffInfo(int pagenum, int pagesize);
    public boolean editStaffInfo(staffInfo staffInfo);
    public staffInfo getStaffInfoById(int id);
    public staffInfo getStaffInfoByAccountId(int id);
    public boolean delStaffInfo(staffInfo staffInfo);
    public boolean delStaffInfo(int id);
}
