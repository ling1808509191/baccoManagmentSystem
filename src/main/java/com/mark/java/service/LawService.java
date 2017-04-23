package com.mark.java.service;

import com.mark.java.dataBean.resultBean;

/**
 * Created by vcc on 2017/3/23.
 */
public interface LawService {
    public resultBean addLawCategory(String lawCategory);
    public resultBean delLawCategory(int categoryId);
    public resultBean getLawCategoryList(int pagenum,int pagesize);
    public resultBean getLawById(int lawid);
    public resultBean addLegalInstrument(int categoryId, String title, String content, int status);
    public resultBean editLegalInstrument(int legalInstrumentId, String title, String content);
    public resultBean publishLegal(int legalInstrumentId);
    public resultBean delLegalInstrument(int legalId);
    public resultBean getOneTypeLaws(int LawCategoryId);
    public resultBean getAllLegalInstrument(int uid,int pagenum,int pagesize);
}
