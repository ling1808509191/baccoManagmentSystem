package com.mark.java.DAO;






import com.mark.java.entity.caseInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface caseInfoDao {
    public int save(caseInfo account);
    public HashMap<String,Object> findCaseInfo(int pagenum, int pagesize);
    public boolean editCaseInfo(caseInfo caseInfo);
    public  HashMap<String,Object>  getUserCaseInfos(int uid ,int pagenum,int pagesize);
    public caseInfo getCaseInfoById(int id);
    public caseInfo getCaseInfoByCaseNum(String CaseNum);
    public boolean delCaseInfo(caseInfo caseInfo);
    public boolean delCaseInfoByCaseNum(String caseInfoNum);
    public boolean delCaseInfo(int id);
}
