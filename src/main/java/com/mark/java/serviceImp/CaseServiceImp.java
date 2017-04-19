package com.mark.java.serviceImp;

import com.mark.java.DAOImp.*;
import com.mark.java.Exceptions.MutilBeanException;
import com.mark.java.dataBean.resultBean;
import com.mark.java.dataBean.upLoadPicBean;
import com.mark.java.entity.*;
import com.mark.java.service.AccountService;
import com.mark.java.service.CaseService;
import com.mark.java.staticTool.staticToll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vcc on 2016/10/22.
 */
@Service
@Transactional
public class CaseServiceImp implements CaseService {


    @Autowired
    private com.mark.java.DAOImp.AccountDAOImp AccountDAOImp;

    @Autowired
    private DepartmentDaoImp departmentDaoImp;

    @Autowired
    private StaffInfoDaoImp staffInfoDaoImp;
    @Autowired
    private CaseInfoDAOImp CaseInfoDAOImp;
    @Autowired
    private TobaccoDaoImp TobaccoDaoImp;
    @Autowired
    private barcodeDaoImp barcodeDaoImp;

    public com.mark.java.DAOImp.CaseInfoDAOImp getCaseInfoDAOImp() {
        return CaseInfoDAOImp;
    }

    public void setCaseInfoDAOImp(com.mark.java.DAOImp.CaseInfoDAOImp caseInfoDAOImp) {
        CaseInfoDAOImp = caseInfoDAOImp;
    }

    public com.mark.java.DAOImp.TobaccoDaoImp getTobaccoDaoImp() {
        return TobaccoDaoImp;
    }

    public void setTobaccoDaoImp(com.mark.java.DAOImp.TobaccoDaoImp tobaccoDaoImp) {
        TobaccoDaoImp = tobaccoDaoImp;
    }

    public com.mark.java.DAOImp.barcodeDaoImp getBarcodeDaoImp() {
        return barcodeDaoImp;
    }

    public void setBarcodeDaoImp(com.mark.java.DAOImp.barcodeDaoImp barcodeDaoImp) {
        this.barcodeDaoImp = barcodeDaoImp;
    }

    public com.mark.java.DAOImp.AccountDAOImp getAccountDAOImp() {
        return AccountDAOImp;
    }

    public void setAccountDAOImp(com.mark.java.DAOImp.AccountDAOImp accountDAOImp) {
        AccountDAOImp = accountDAOImp;
    }

    public DepartmentDaoImp getDepartmentDaoImp() {
        return departmentDaoImp;
    }

    public void setDepartmentDaoImp(DepartmentDaoImp departmentDaoImp) {
        this.departmentDaoImp = departmentDaoImp;
    }

    public StaffInfoDaoImp getStaffInfoDaoImp() {
        return staffInfoDaoImp;
    }

    public void setStaffInfoDaoImp(StaffInfoDaoImp staffInfoDaoImp) {
        this.staffInfoDaoImp = staffInfoDaoImp;
    }

    public resultBean casesUpload(upLoadPicBean upLoadPicBean) {
        Integer uid=upLoadPicBean.getUser_id();
        Integer departmentId=upLoadPicBean.getDepartment_id();
        resultBean resultBean=new resultBean();
        caseInfo caseInfo=CaseInfoDAOImp.getCaseInfoByCaseNum(upLoadPicBean.getNum());
        if(uid==null||departmentId==null){
        resultBean.setSucess(0);
            resultBean.setMessage("uid or departmentId is empty");
            return resultBean;
        }else if(caseInfo!=null){
            resultBean.setSucess(0);
            resultBean.setMessage("case num is already exited");
            return resultBean;
        }
        caseInfo =new caseInfo();
        Account account=AccountDAOImp.getAccountById(uid);
        department department=departmentDaoImp.getDepartmentById(departmentId);
        if(account==null||department==null){
            resultBean.setSucess(0);
            resultBean.setMessage("can not find account or department by id");
            return resultBean;
        }
        caseInfo.setAccount(account);
        caseInfo.setmDepartment(department);
        caseInfo.setSubmit_time(upLoadPicBean.getDate());
        caseInfo.setCaseInfoNum(upLoadPicBean.getNum());
        Calendar a=Calendar.getInstance();
        caseInfo.setYear(a.get(Calendar.YEAR));
        Integer caseInfoId= CaseInfoDAOImp.save(caseInfo);
        if(caseInfoId==null||caseInfoId<=0){
            resultBean.setSucess(0);
            resultBean.setMessage("save caseInfo error");
            return resultBean;
        }
        if(upLoadPicBean.getCigaretteList()!=null&&upLoadPicBean.getCigaretteList().size()>0){
            for(int i=0;i<upLoadPicBean.getCigaretteList().size();i++){
                tobacco mtobacco=new tobacco();
                barcode mbarcode=barcodeDaoImp.getBarcodeByCodeNum(upLoadPicBean.getCigaretteList().get(i).getBarcode());
                if(mbarcode==null){
                    resultBean.setSucess(0);
                    resultBean.setMessage("get barcode error");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
                mtobacco.setBarcode(mbarcode);                      //ÉèÖÃÏãÑÌÌõÐÎÂë
                mtobacco.setmCase_info(CaseInfoDAOImp.getCaseInfoById(caseInfoId));             //ÉèÖÃÏãÑÌËùÊô°´¼üÍâ¼ü
                mtobacco.setLaserCodeNum(upLoadPicBean.getCigaretteList().get(i).getBarcode());         //ÉèÖÃ¼¤¹âÂëÄÚÈÝ
                String laserCodeImgName="laserImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getLaserCodeImg(),laserCodeImgName);
                mtobacco.setLaserCodeUrl(laserCodeImgName); //ÉèÖÃ¼¤¹âÂëÕÕÆ¬Â·¾¶
                String tobaccoImgName1 ="tobaccoImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getImage1(),tobaccoImgName1);
                String tobaccoImgName2 ="tobaccoImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getImage1(),tobaccoImgName2);
                mtobacco.setImagurls(tobaccoImgName1+";"+tobaccoImgName2);                       //ÉèÖÃÏãÑÌÍâ¹ÛÕÕÆ¬Â·¾¶
                Integer tobaccoId=TobaccoDaoImp.save(mtobacco);
                if(tobaccoId==null||tobaccoId<=0){
                    resultBean.setSucess(0);
                    resultBean.setMessage("get barcode error");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
              }
            }
            resultBean.setSucess(1);
        resultBean.setMessage("save caseInfo sucess");
        return resultBean;
        }



    public resultBean delCases(List<String> casesList,Integer uid) {
        Account account=AccountDAOImp.getAccountById(uid);
        resultBean resultBean=new resultBean();
        if(account==null){
            resultBean.setSucess(0);
            resultBean.setMessage("get a wrong uid");
            return resultBean;
        }
        if(casesList==null||casesList.size()==0){
            resultBean.setSucess(0);
            resultBean.setMessage("empty caseList");
            return resultBean;
        }
        for(int i=0;i<casesList.size();i++){
            if(account.is_admin()){
                if(!CaseInfoDAOImp.delCaseInfoByCaseNum(casesList.get(i))){
                    resultBean.setSucess(0);
                    resultBean.setMessage("delete caseInfo unsucessful");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
            }else{
                caseInfo tempCaseInfo=CaseInfoDAOImp.getCaseInfoByCaseNum(casesList.get(i));
                if(tempCaseInfo!=null&&tempCaseInfo.getAccount().getUid()==account.getUid()){
                   if(!CaseInfoDAOImp.delCaseInfo(tempCaseInfo)) {
                       resultBean.setSucess(0);
                       resultBean.setMessage("delete caseInfo unsucessful");
                       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                       return resultBean;
                   }
                }
            }

        }
        resultBean.setSucess(1);
        resultBean.setMessage("delete caseInfo sucess");
        return resultBean;
    }

    public resultBean getUserCasesList(Integer uid){
        resultBean resultBean=new resultBean();
        resultBean.setSucess(1);
        resultBean.setMessage("get case list sucess");
        resultBean.getData().add(CaseInfoDAOImp.getUserCaseInfos(uid));
        return resultBean;
    }

    public resultBean getTobaccoListByCaseId(String caseNum) {
        resultBean resultBean=new resultBean();
        if(caseNum==null){
            resultBean.setSucess(0);
            resultBean.setMessage("cannot get case number");
            return resultBean;
        }
        resultBean.setSucess(1);
        resultBean.setMessage("get tobacco list sucess");
        resultBean.getData().add(TobaccoDaoImp.getTobaccosListByCaseNum(caseNum));
        return resultBean;
    }

    public resultBean get(Integer uid) {
        return null;
    }
}
