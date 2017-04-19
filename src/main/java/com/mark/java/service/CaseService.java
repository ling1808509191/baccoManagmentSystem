package com.mark.java.service;

import com.mark.java.dataBean.resultBean;
import com.mark.java.dataBean.upLoadPicBean;

import java.util.List;

/**
 * Created by vcc on 2017/4/9.
 */
public interface CaseService {
    public resultBean casesUpload(upLoadPicBean upLoadPicBean);
    public resultBean delCases(List<String> casesList,Integer uid);
    public resultBean getUserCasesList(Integer uid);
    public resultBean getTobaccoListByCaseId(String caseNum);
    public resultBean get(Integer uid);
}
