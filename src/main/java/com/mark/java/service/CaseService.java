package com.mark.java.service;

import com.mark.java.dataBean.resultBean;
import com.mark.java.dataBean.upLoadPicBean;

import java.util.List;

/**
 * Created by vcc on 2017/4/9.
 */
public interface CaseService {
    public resultBean casesUpload(upLoadPicBean upLoadPicBean,boolean updateCase);
    public resultBean delCases(List<String> casesList,Integer uid);
    public resultBean getUserCasesList(Integer uid,int pagenum,int pagesize);
    public resultBean getAllCasesList(int pagenum,int pagesize);
    public resultBean getTobaccoListByCaseId(String caseNum);
    public resultBean get(Integer uid);
    public resultBean editTobaccoLaserCode(int tobaccoId,String laserCode,int uid);
}
