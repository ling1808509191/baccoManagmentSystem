package com.mark.java.serviceImp;

import com.mark.java.DAOImp.AccountDAOImp;
import com.mark.java.DAOImp.LawCategoryDaoImp;
import com.mark.java.DAOImp.LawDaoImp;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.Account;
import com.mark.java.entity.Law;
import com.mark.java.entity.LawCategory;
import com.mark.java.entity.department;
import com.mark.java.service.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vcc on 2017/4/1.
 */
@Service
@Transactional
public class LawServiceImp implements LawService{
    @Autowired
    private LawDaoImp lawDaoImp;
    @Autowired
    private LawCategoryDaoImp LawCategoryDaoImp;
    @Autowired
    private AccountDAOImp accountDAOImp;

    public resultBean addLawCategory(String name) {
        resultBean resultBean=new resultBean();
        LawCategory templawCategory=LawCategoryDaoImp.getLawCategoryByName(name);
        if(templawCategory!=null){
            resultBean.setSucess(0);
            resultBean.setMessage("this name is already exited");
            return resultBean;
        }
        templawCategory=new LawCategory();
        templawCategory.setName(name);
        int lawCategoryId=LawCategoryDaoImp.save(templawCategory);
       if(lawCategoryId>0){
           resultBean.setSucess(1);
        resultBean.setMessage("add LawCategory sucess");
        resultBean.getData().add(LawCategoryDaoImp.getLawCategoryById(lawCategoryId));
        return resultBean;
       }
        else{
           resultBean.setSucess(0);
           resultBean.setMessage("add LawCategory faild");
           return resultBean;
       }
    }

    public resultBean delLawCategory(int categoryId) {
        resultBean resultBean=new resultBean();
        LawCategory templawCategory=LawCategoryDaoImp.getLawCategoryById(categoryId);
        if(templawCategory==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law category");
            return resultBean;
        }

        if(LawCategoryDaoImp.delLawCategory(categoryId)){
            resultBean.setSucess(1);
            resultBean.setMessage("ddelete lawCategory sucess");
            return resultBean;
        }else{
            resultBean.setSucess(0);
            resultBean.setMessage("ddelete lawCategory faild");
            return resultBean;
        }
    }

    public resultBean getLawCategoryList(int pagenum,int pagesize) {
        resultBean resultBean=new resultBean();
         resultBean.getData().add(LawCategoryDaoImp.findLawCategory(pagenum,pagesize));
        resultBean.setSucess(1);
        resultBean.setMessage("get law category list sucess");
        return resultBean;
    }

    public resultBean getLawById(int lawid) {
        resultBean resultBean=new resultBean();
        Law tempLaw=lawDaoImp.getLawById(lawid);
        if(tempLaw!=null){
            resultBean.setSucess(1);
            resultBean.getData().add(tempLaw);
            resultBean.setMessage("get law by id sucess");
            return resultBean;
        }else{
            resultBean.setSucess(0);
            resultBean.setMessage("get law by id  faild");
            return resultBean;
        }

    }

    public resultBean addLegalInstrument(int categoryId, String title, String content, int status) {
        resultBean resultBean=new resultBean();
        LawCategory templawCategory=LawCategoryDaoImp.getLawCategoryById(categoryId);
        if(templawCategory==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law category");
            return resultBean;
        }
        Law tempLaw=new Law();
        tempLaw.setmLawCategory(templawCategory);
        tempLaw.setTitle(title);
        tempLaw.setStatus(status);
        tempLaw.setContent(content);
        int tempLawId =lawDaoImp.save(tempLaw);
        if(tempLawId>0){
            resultBean.setSucess(1);
            resultBean.setMessage("add Law sucess");
            return resultBean;
        }else{
            resultBean.setSucess(0);
            resultBean.setMessage("ad Law faild");
            return resultBean;
        }

    }

    public resultBean editLegalInstrument(int legalInstrumentId, String title, String content) {
        resultBean resultBean=new resultBean();
        Law tempLaw=lawDaoImp.getLawById(legalInstrumentId);
        if(tempLaw==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law ");
            return resultBean;
        }
        if(title!=null){
            tempLaw.setTitle(title);
        }
        if(content!=null){
            tempLaw.setContent(content);
        }
        if(lawDaoImp.editLaw(tempLaw)){
            resultBean.setSucess(1);
            resultBean.setMessage("edit law sucess");
            return resultBean;
        }else{
            resultBean.setMessage("eidt law faild");
            resultBean.setSucess(0);
            return resultBean;
        }
    }

    public resultBean publishLegal(int legalInstrumentId) {

        resultBean resultBean=new resultBean();
        Law tempLaw=lawDaoImp.getLawById(legalInstrumentId);
        if(tempLaw==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law ");
            return resultBean;
        }else{
            tempLaw.setStatus(1);
           if(lawDaoImp.editLaw(tempLaw)) {
               resultBean.setSucess(1);
               resultBean.setMessage("published law sucess");
               return resultBean;
           }else{
               resultBean.setSucess(0);
               resultBean.setMessage("published law faild ");
               return resultBean;
            }
        }

    }

    public resultBean delLegalInstrument(int legalId) {
        resultBean resultBean=new resultBean();
        Law tempLaw=lawDaoImp.getLawById(legalId);
        if(tempLaw==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law ");
            return resultBean;
        }else{
            if(lawDaoImp.delLaw(legalId)){
                resultBean.setSucess(1);
                resultBean.setMessage("delete law sucess");
                return resultBean;
            }else{
                resultBean.setSucess(0);
                resultBean.setMessage("delete law faild ");
                return resultBean;
            }

        }
    }

    public resultBean getOneTypeLaws(int LawCategoryId) {
        resultBean resultBean=new resultBean();
        LawCategory tempLawcategory=LawCategoryDaoImp.getLawCategoryById(LawCategoryId);
        if(tempLawcategory==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not found this law category ");
            return resultBean;
        }else{
            List<Law> resultList=lawDaoImp.getLawByCategoryId(LawCategoryId);
            resultBean.setSucess(1);
            resultBean.setMessage("get law list sucess");
            resultBean.getData().add(resultList);
            return resultBean;
        }
    }

    public resultBean getAllLegalInstrument(int uid, int pagenum, int pagesize) {
      Account account= accountDAOImp.getAccountById(uid);
        resultBean resultBean=new resultBean();
        if(account==null){
            resultBean.setSucess(0);
            resultBean.setMessage("could not found this account");
            return resultBean;

        }
        if(account.is_admin()){
        resultBean.setSucess(1);
            resultBean.setMessage("found published law list sucess");
            resultBean.getData().add(lawDaoImp.getPublishedLaw(pagenum,pagesize));
            return resultBean;
        }else{
            resultBean.setSucess(1);
           resultBean.setMessage("found All law list sucess");
            resultBean.getData().add(lawDaoImp.findLaw(pagenum,pagesize));
            return resultBean;
        }
    }
}
