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

    public resultBean casesUpload(upLoadPicBean upLoadPicBean,boolean updateCase) {
        Integer uid=upLoadPicBean.getUser_id();
        Integer departmentId=upLoadPicBean.getDepartment_id();
        resultBean resultBean=new resultBean();
        String subtime=null;
        caseInfo caseInfo=CaseInfoDAOImp.getCaseInfoByCaseNum(upLoadPicBean.getNum());
        if(uid==null||departmentId==null){
        resultBean.setSuccess(0);
            resultBean.setMessage("uid or departmentId is empty");
            return resultBean;
        }else if(caseInfo!=null&&!updateCase){
            resultBean.setSuccess(0);
            resultBean.setMessage("case num is already exited");
            return resultBean;
        }else if(caseInfo!=null&&updateCase){
                List<tobacco> TobaccosList=TobaccoDaoImp.getTobaccosListByCaseNum(caseInfo.getCaseInfoNum());
            for(int i=0;i<TobaccosList.size();i++){
                tobacco tempTobacco=TobaccosList.get(i);
                String LaserCodeFileName=tempTobacco.getLaserCodeUrl();
                staticToll.deleteFile(LaserCodeFileName);
                String [] imgs=tempTobacco.getImagurls().split(";");
                for(int j=0;j<imgs.length;j++){
                    String fileName=imgs[j];
                    staticToll.deleteFile(fileName);
                }
            }
            subtime=caseInfo.getSubmit_time();
            CaseInfoDAOImp.delCaseInfo(caseInfo);
            caseInfo=null;
        }
          caseInfo =new caseInfo();
        Account account=AccountDAOImp.getAccountById(uid);
        department department=departmentDaoImp.getDepartmentById(departmentId);
        if(account==null||department==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("can not find account or department by id");
            return resultBean;
        }
        caseInfo.setAccount(account);
        caseInfo.setDepartment(department);
        caseInfo.setTimeStamp(System.currentTimeMillis());
        if(!updateCase)
        {
            caseInfo.setSubmit_time(upLoadPicBean.getDate());
        }else{
            caseInfo.setSubmit_time(subtime);
        }

        caseInfo.setCaseInfoNum(upLoadPicBean.getNum());
        caseInfo.setTotalCigaretteNum(upLoadPicBean.getTotal_num());
        caseInfo.setYear(upLoadPicBean.getYear());
        Integer caseInfoId= CaseInfoDAOImp.save(caseInfo);
        if(caseInfoId==null||caseInfoId<=0){
            resultBean.setSuccess(0);
            resultBean.setMessage("save caseInfo error");
            return resultBean;
        }
        if(upLoadPicBean.getCigaretteList()!=null&&upLoadPicBean.getCigaretteList().size()>0){
            for(int i=0;i<upLoadPicBean.getCigaretteList().size();i++){
                tobacco mtobacco=new tobacco();
                barcode mbarcode=barcodeDaoImp.getBarcodeByCodeNum(upLoadPicBean.getCigaretteList().get(i).getBarcode());
                if(mbarcode==null){
                    resultBean.setSuccess(0);
                    resultBean.setMessage("get barcode error");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
                mtobacco.setBarcode(mbarcode);                      //设置香烟条形码
                mtobacco.setmCase_info(CaseInfoDAOImp.getCaseInfoById(caseInfoId));             //设置香烟所属按键外键
                mtobacco.setLaserCodeNum(upLoadPicBean.getCigaretteList().get(i).getBarcode());         //设置激光码内容
                String laserCodeImgName="laserImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getLaserCodeImg(),laserCodeImgName);
                mtobacco.setLaserCodeUrl(laserCodeImgName); //设置激光码照片路径
                String tobaccoImgName1 ="tobaccoImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getImage1(),tobaccoImgName1);
                String tobaccoImgName2 ="tobaccoImg"+System.currentTimeMillis()+".jpg";
                staticToll.GenerateImage(upLoadPicBean.getCigaretteList().get(i).getImage1(),tobaccoImgName2);
                mtobacco.setImagurls(tobaccoImgName1+";"+tobaccoImgName2);                       //设置香烟外观照片路径
                Integer tobaccoId=TobaccoDaoImp.save(mtobacco);
                if(tobaccoId==null||tobaccoId<=0){
                    resultBean.setSuccess(0);
                    resultBean.setMessage("get barcode error");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
              }
            }
            resultBean.setSuccess(1);
        resultBean.setMessage("save caseInfo sucess");
        return resultBean;
        }



    public resultBean delCases(List<String> casesList,Integer uid) {
        Account account=AccountDAOImp.getAccountById(uid);
        resultBean resultBean=new resultBean();
        if(account==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("get a wrong uid");
            return resultBean;
        }
        if(casesList==null||casesList.size()==0){
            resultBean.setSuccess(0);
            resultBean.setMessage("empty caseList");
            return resultBean;
        }
        for(int i=0;i<casesList.size();i++){
            if(account.is_admin()){
                if(!CaseInfoDAOImp.delCaseInfoByCaseNum(casesList.get(i))){
                    resultBean.setSuccess(0);
                    resultBean.setMessage("delete caseInfo unsucessful");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultBean;
                }
            }else{
                caseInfo tempCaseInfo=CaseInfoDAOImp.getCaseInfoByCaseNum(casesList.get(i));
                if(tempCaseInfo!=null&&tempCaseInfo.getAccount().getUid()==account.getUid()){
                   if(!CaseInfoDAOImp.delCaseInfo(tempCaseInfo)) {
                       resultBean.setSuccess(0);
                       resultBean.setMessage("delete caseInfo unsucessful");
                       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                       return resultBean;
                   }
                }
            }
        }
        resultBean.setSuccess(1);
        resultBean.setMessage("delete caseInfo sucess");
        return resultBean;
    }

    public resultBean getUserCasesList(Integer uid,int pagenum,int pagesize){
        resultBean resultBean=new resultBean();
        resultBean.setSuccess(1);
        resultBean.setMessage("get case list sucess");
        resultBean.getData().add(CaseInfoDAOImp.getUserCaseInfos(uid,pagenum,pagesize));
        return resultBean;
    }

    public resultBean getAllCasesList(int pagenum, int pagesize) {
        resultBean resultBean=new resultBean();
        resultBean.setSuccess(1);
        resultBean.setMessage("get All cases sucess");
        resultBean.getData().add(CaseInfoDAOImp.findCaseInfo(pagenum,pagesize));
        return resultBean;
    }

    public resultBean getTobaccoListByCaseId(String caseNum) {
        resultBean resultBean=new resultBean();
        if(caseNum==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("cannot get case number");
            return resultBean;
        }
        resultBean.setSuccess(1);
        resultBean.setMessage("get tobacco list sucess");
        resultBean.getData().add(TobaccoDaoImp.getTobaccosListByCaseNum(caseNum));
        return resultBean;
    }

    public resultBean get(Integer uid) {
        return null;
    }

    public resultBean editTobaccoLaserCode(int tobaccoId, String laserCode,int uid) {
        tobacco mtobacco=TobaccoDaoImp.getTobaccoById(tobaccoId);
        resultBean resultBean=new resultBean();
        Account mAccount=AccountDAOImp.getAccountById(uid);

        if(mtobacco==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("获取烟草信息失败");
            return resultBean;
        }
        if(mAccount==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("账号异常");
            return resultBean;
        }
        if((!mAccount.is_admin())&&mtobacco.getmCase_info().getAccount().getUid()!=uid){
            resultBean.setSuccess(0);
            resultBean.setMessage("用户权限不足");
            return resultBean;
        }
        mtobacco.setLaserCodeNum(laserCode);
        if(TobaccoDaoImp.editTobacco(mtobacco)){
            resultBean.setSuccess(1);
            resultBean.setMessage("更新激光码成功");
            return resultBean;
        }else{
            resultBean.setSuccess(0);
            resultBean.setMessage("更新激光码失败");
            return resultBean;
        }

    }
}
