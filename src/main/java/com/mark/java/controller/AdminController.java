package com.mark.java.controller;

import com.mark.java.dataBean.publishRequestBean;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.barcode;
import com.mark.java.entity.notification;
import com.mark.java.entity.notificationUser;
import com.mark.java.serviceImp.*;
import com.mark.java.staticTool.staticToll;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vcc on 2017/3/31.
 */
@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private AccountServiceImp AccountService;
    @Autowired
    private DepartmentServiceImp departmentService;
    @Autowired
    private LawServiceImp LawServiceImp;
    @Autowired
    private NotificationServiceImp mNotificationServiceImp;
    @Autowired
    private AppVersionServiceImp appVersionServiceImp;

    @Autowired
    private CaseServiceImp mCaseServiceImp;
    @Autowired
    private BarcodeServiceImp BarcodeServiceImp;

    public com.mark.java.serviceImp.BarcodeServiceImp getBarcodeServiceImp() {
        return BarcodeServiceImp;
    }

    public void setBarcodeServiceImp(com.mark.java.serviceImp.BarcodeServiceImp barcodeServiceImp) {
        BarcodeServiceImp = barcodeServiceImp;
    }
    public CaseServiceImp getmCaseServiceImp() {
        return mCaseServiceImp;
    }

    public void setmCaseServiceImp(CaseServiceImp mCaseServiceImp) {
        this.mCaseServiceImp = mCaseServiceImp;
    }


    public AppVersionServiceImp getAppVersionServiceImp() {
        return appVersionServiceImp;
    }

    public void setAppVersionServiceImp(AppVersionServiceImp appVersionServiceImp) {
        this.appVersionServiceImp = appVersionServiceImp;
    }

    public com.mark.java.serviceImp.LawServiceImp getLawServiceImp() {
        return LawServiceImp;
    }

    public void setLawServiceImp(com.mark.java.serviceImp.LawServiceImp lawServiceImp) {
        LawServiceImp = lawServiceImp;
    }

    public NotificationServiceImp getmNotificationServiceImp() {
        return mNotificationServiceImp;
    }

    public void setmNotificationServiceImp(NotificationServiceImp mNotificationServiceImp) {
        this.mNotificationServiceImp = mNotificationServiceImp;
    }

    public AccountServiceImp getAccountService() {
        return AccountService;
    }

    public void setAccountService(AccountServiceImp accountService) {
        AccountService = accountService;
    }

    public DepartmentServiceImp getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentServiceImp departmentService) {
        this.departmentService = departmentService;
    }
    /*
        -----------------------------------------Account start---------------------------------------------
         */
    @RequestMapping("/addAccount")
    @ResponseBody
    public resultBean addAccount(@RequestBody Map map){
        resultBean resultBean=new resultBean();
        return AccountService.addAccount( (String)map.get("username"),   (String)map.get("password"),(Boolean)map.get("is_admin"),
                (String)map.get("name"),(Integer)map.get( "department_id"), (String)map.get("phone"),
                (String)map.get("email"), (String)map.get("identity_num"));
    }
    @RequestMapping("/searchAcount")
    @ResponseBody
    public resultBean searchAcount(@RequestBody Map map){
        Integer uid=null;
        String username=null;

        Boolean is_admin=null;

        Integer status=null;

        String name=null;

        String phone=null;

        Integer departmentId=null;
        try{
            uid= (Integer)map.get("uid");
        }catch (NullPointerException e){
            uid=null;
        }
        try{
            username= (String)map.get("username");
        }catch (NullPointerException e){
            username="";
        }finally {
            if(username==null){
                username="";
            }
        }
        try{
            is_admin=  (Boolean)map.get("is_admin");
        }catch (NullPointerException e){
            is_admin=null;
        }

        try{
            status=  (Integer) map.get("status");
        }catch (NullPointerException e){
            status=null;
        }

        try{
            name=  (String)map.get("name");
        }catch (NullPointerException e){
            name="";
        }
        finally {
            if(name==null){
                name="";
            }
        }
        try{
            phone=(String)map.get("phone");
        }catch (NullPointerException e){
            phone="";
        }
        finally {
            if(phone==null){
                phone="";
            }
        }
        try{
            departmentId= (Integer)map.get("department_id");
        }catch (NullPointerException e){
            departmentId=null;
        }

        if (uid == null) {
            if(departmentId==null){
                return AccountService.SearchAccount(username,is_admin,status,name,phone);
            }else{
                return AccountService.SearchAccountWithDepartmentId(username,is_admin,status,name,phone,departmentId);
            }
        }else{
            return AccountService.getAccountById(uid);
        }

    }
    @RequestMapping("/getAllUser")
    @ResponseBody
    public resultBean getAllUser(@RequestParam Integer pagenum,@RequestParam Integer pagesize){
        return AccountService.getAllAccount(pagenum,pagesize);

    }
    @RequestMapping("/editAccountStatus")
    @ResponseBody
    public resultBean editAccountStatus(@RequestBody Map map){

        return AccountService.editAccountStatus( (Integer) map.get("uid"),(Integer)map.get("status"));
    }
    /*
    -----------------------------------------Account End---------------------------------------------
    -----------------------------------------department start---------------------------------------------
     */
    @RequestMapping("/searchDepartment")
    @ResponseBody
    public resultBean searchDepartment(@RequestBody Map map){
        Integer deparmentId=null;
        String name=null;
        String phone=null;
        try{
            deparmentId= (Integer)map.get("department_id");
        }catch (Exception e){
            deparmentId=null;
        }

        try{
            name= (String)map.get("name");
        }catch (Exception e){
            name=null;
        }
        finally {
            if(name==null)
                name="";
        }
        try{
            phone= (String)map.get("phone");
        }catch (Exception e){
            phone=null;
        }
        finally {
            if(phone==null){
                phone="";
            }
        }
        if(deparmentId==null)
            return departmentService.searchDepartment(name,phone);
        else{
            return departmentService.getDepartmentById(deparmentId);
        }
    }

    @RequestMapping("/editDepartment")
    @ResponseBody
    public resultBean editDepartment(@RequestBody Map map){
        String name=null;
        String phone=null;
        String description=null;
        try{
             name=(String)map.get("name");
        }catch (Exception e){
            name=null;
        }
        try{
            phone=(String)map.get("phone");
        }catch (Exception e){
            phone=null;
        } try{
            description=(String)map.get("description");
        }catch (Exception e){
            description=null;
        }
        return departmentService.editDepartment( (Integer) map.get("departmnet_id"),  name ,phone,description);
    }
    @RequestMapping("/addDepartment")
    @ResponseBody
    public resultBean addDepartment(@RequestBody Map map){
        return departmentService.addDepartment((String)map.get("name"),(String)map.get("description"),(String)map.get("phone"));


    }
    @RequestMapping("/getAllDepartment")
    @ResponseBody
    public resultBean getAllDepartment(@RequestParam int pagenum,@RequestParam int pagesize){

        return departmentService.getAllDepartments(pagenum,pagesize);
    }
    /*
     ----------------------------------------department End---------------------------------------------
     -----------------------------------------law start---------------------------------------------
      */
    @RequestMapping("/addLawCategory")
    @ResponseBody
    public resultBean addLawCategory(@RequestBody Map map){
      String name=(String)map.get("name");
        return LawServiceImp.addLawCategory(name);
    }
    @RequestMapping("/delLawCategory")
    @ResponseBody
    public resultBean delLawCategory(@RequestBody Map map){
        Integer category_id=(Integer)map.get("category_id");
        return LawServiceImp.delLawCategory(category_id);
    }
    @RequestMapping("/getCategoryList")
    @ResponseBody
    public resultBean getCategoryList(@RequestParam int pagenum,@RequestParam int pagesize){

        return LawServiceImp.getLawCategoryList(pagenum,pagesize);
    }
    @RequestMapping(value = "/uploadApk")
    @ResponseBody
    public resultBean upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        resultBean resultBean =new resultBean();
        System.out.println("��ʼ");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String version=multipartRequest.getParameter("version");
        String description=multipartRequest.getParameter("description");
        String path =System.getProperty("evan.webapp")+"apks"+File.separatorChar;
        String fileName = file.getOriginalFilename();
        if(fileName.contains(".apk")){
            fileName="apk"+System.currentTimeMillis()+".apk";
        }else{
            resultBean tempResultBean=new resultBean();
            tempResultBean.setSuccess(0);
            tempResultBean.setMessage("���ϴ�apk�ļ�");
            return tempResultBean;
        }
//        String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //����
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultBean tempResultBean= appVersionServiceImp.saveApp(fileName,description,version);
        if(tempResultBean.getSuccess()==1){
            return tempResultBean;
        }else{
            staticToll.deleteFile(fileName);
            return tempResultBean;
        }
    }
    @RequestMapping(value = "/uploadExcel")
    @ResponseBody
    public resultBean uploadExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        resultBean resultBean =new resultBean();
        System.out.println("��ʼ");
        String path =System.getProperty("evan.webapp")+"excels"+File.separatorChar;
        String fileName = file.getOriginalFilename();
        if(fileName.contains(".xls")){
        }else{
            resultBean tempResultBean=new resultBean();
            tempResultBean.setSuccess(0);
            tempResultBean.setMessage("���ϴ�excel�ļ�");
            return tempResultBean;
        }
//        String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //����
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultBean.setSuccess(1);
        resultBean.setMessage("�����ļ��ɹ�");
        return resultBean;
    }
    @RequestMapping("/addLawInstrument")
    @ResponseBody
    public resultBean addLawInstrument(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");
        String title=(String) map.get("title");
        String content=(String) map.get("content");
        Integer status=(Integer)map.get("status");
        return LawServiceImp.addLegalInstrument(categoryId,title,content,status);
    }
    @RequestMapping("/editLawInstrument")
    @ResponseBody
    public resultBean editLawInstrument(@RequestBody Map map){
        Integer lawID=(Integer)map.get("law_id");
        String title=null;
        String content=null;
        Integer status=null;
        try{
            title= (String) map.get("title");
        }catch (Exception e){
            title=null;
        }
        try{
            content=(String) map.get("content");
        }catch (Exception e){
            content=null;
        }
        try{
            status= (Integer)map.get("status");
        }catch (Exception e){
            title=null;
        }

        return LawServiceImp.editLegalInstrument(lawID,title,content);
    }
    @RequestMapping("/getLawInstrumentById")
    @ResponseBody
    public resultBean getLawInstrumentById(@RequestBody Map map){
        Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.getLawById(lawId);
    }
    @RequestMapping("/delLawInstrument")
    @ResponseBody
    public resultBean delLawInstrument(@RequestBody Map map){
        Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.delLegalInstrument(lawId);
    }
    @RequestMapping("/getLawByCategoryId")
    @ResponseBody
    public resultBean getLawByCategoryId(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");

        return LawServiceImp.getOneTypeLaws(categoryId);
    }

    @RequestMapping("/publishLaw")
    @ResponseBody
    public resultBean publishLaw(@RequestBody Map map){
    Integer lawId=(Integer)map.get("law_id");

        return LawServiceImp.publishLegal(lawId);
    }
    /*
     ----------------------------------------law End---------------------------------------------
     -----------------------------------------notication start---------------------------------------------
      */
    @RequestMapping("/addNotificationCategory")
    @ResponseBody
    public resultBean addNotificationCategory(@RequestBody Map map){
        String name=(String)map.get("name");

        return mNotificationServiceImp.addNotifyCategory(name);
    }
    @RequestMapping("/delNotificationCategory")
    @ResponseBody
    public resultBean delNotificationCategory(@RequestBody Map map){
        Integer categoryId=(Integer) map.get("category_id");

        return mNotificationServiceImp.delNotifyCategory(categoryId);
    }
    @RequestMapping("/addNotifycation")
    @ResponseBody
    public resultBean addNotifycation(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("category_id");
        String title=null;
        String content=null;
        try{
            title= (String)map.get("title");
        }catch (Exception e){
            title=null;
        }
        try{
            content =(String)map.get("content");
        }catch (Exception e){
            content=null;
        }

        return mNotificationServiceImp.addNotification(categoryId,title,content );
    }
    @RequestMapping("/editNotifycation")
    @ResponseBody
    public resultBean editNotifycation(@RequestBody Map map){
        Integer categoryId=(Integer)map.get("notification_id");
        String title=null;
        try{
            title= (String)map.get("title");
        }catch (Exception e){
            title=null;
        }
        String content=null;
        try{
            content =(String)map.get("content");
        }catch (Exception e){
            content=null;
        }
        return mNotificationServiceImp.editNotification(categoryId,title,content );
    }
    @RequestMapping("/getNotificationById")
    @ResponseBody
    public resultBean getNotificationById(@RequestBody Map map){
        Integer notificationId=null;
        try{
            notificationId =   (Integer)map.get("notification_id");
        }catch (Exception e){
            notificationId=null;
        }
        return mNotificationServiceImp.getNotificationById(notificationId);
    }
    @RequestMapping("/publishNotification")
    @ResponseBody
    public resultBean publishNotification(@RequestBody  publishRequestBean requestBean){
        Integer notificationId=null;
        Integer departmentId=null;
        return mNotificationServiceImp.publishNotification(requestBean.getNotification_id(),requestBean.getDepartment_ids());
    }
    @RequestMapping("/delNotification")
    @ResponseBody
    public resultBean delNotification(@RequestBody Map map){
        Integer notificationId=null;
       try{
           notificationId=(Integer)map.get("notification_id");
       }catch (Exception e){
           notificationId=null;
       }

        return mNotificationServiceImp.delNotification(notificationId);
    }
    @RequestMapping("/getAllCases")
    @ResponseBody
    public resultBean getAllCases(@RequestParam int pagenum,@RequestParam int pagesize){

        return mCaseServiceImp.getAllCasesList(pagenum,pagesize);
    }
    @RequestMapping("/getNotificationByCategory")
    @ResponseBody
    public resultBean getNotificationByCategory(@RequestParam int pagenum,@RequestParam int pagesize,@RequestBody Map map){
        Integer categoryId=null;
        try{
            categoryId=(Integer)map.get("category_id");
        }catch (Exception e){
            categoryId=null;
        }

        return mNotificationServiceImp.getTypeNotifications(categoryId,pagenum,pagesize);
    }
    @RequestMapping("/getAllApkRecords")
    @ResponseBody
    public resultBean getAllApkVersions(@RequestParam int pagenum,@RequestParam int pagesize){

        return appVersionServiceImp.getAllAppVersion(pagenum,pagesize);
    }
    @RequestMapping("/addBarcode")
    @ResponseBody
    public resultBean addBarcode(@RequestBody Map file) {
        resultBean resultBean=new resultBean();
        String Number=(String)file.get("number");
        String Name=(String)file.get("name");
        Double whoPrice=(Double)file.get("whoPrice");
        Double referPrice=(Double)file.get("referPrice");
        String specifications=(String)file.get("specifications");
        barcode barcode=BarcodeServiceImp.getbarcodeBybarcodeNum(Number);
        if(barcode==null){
            barcode=new barcode();
            barcode.setBarcodeNum(Number);
        }
        barcode.setName(Name);
        barcode.setSpecifications(specifications);
        barcode.setWholesalesPrice(whoPrice);
        barcode.setReferenceRetailPrice(referPrice);
        BarcodeServiceImp.saveBarcode(barcode);
        resultBean.setSuccess(1);
        resultBean.setMessage("save barcode success");
        return resultBean;
    }
    @RequestMapping("/readBarcodeEccel")
    @ResponseBody
    public resultBean readBarcodeEccel(@RequestBody Map file) {
        resultBean resultBean=new resultBean();
        String filename = (String) file.get("fileName");
        int rowStart = (Integer) file.get("rowStart");
        int rowEnd = (Integer) file.get("rowEnd");
        int colNumber = (Integer) file.get("colNumber");
        int colName = (Integer) file.get("colName");
        int colWhol = (Integer) file.get("colWhol");
        int colspec=(Integer) file.get("colspec");
        int colRef = (Integer) file.get("colRef");
        String imgFilePath =System.getProperty("evan.webapp")+"excels"+File.separatorChar+filename;
        File xlsFile = new File(imgFilePath);
        // ��ù���������
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(xlsFile);
        } catch (Exception e) {
            resultBean.setSuccess(0);
            resultBean.setMessage("��ȡExcel�ļ�ʧ��");
            return resultBean;
        }

        // ������й�����
        Sheet[] sheets = workbook.getSheets();
        // ����������
        if (sheets != null) {
            for (Sheet sheet : sheets) {

                // ��ȡ����
                for (int row = rowStart; row < rowEnd; row++) {
                    barcode barcode=null;
                    String barcodeNum= sheet.getCell(colNumber, row).getContents();
                    barcode=BarcodeServiceImp.getbarcodeBybarcodeNum(barcodeNum);
                    if(barcode==null){
                        barcode=  new barcode();
                        barcode.setBarcodeNum(barcodeNum);
                    }
                    barcode.setName( sheet.getCell(colName, row).getContents());
                    if(colWhol!=-1) {
                        try {
                            barcode.setWholesalesPrice(Double.valueOf(sheet.getCell(colWhol, row).getContents().replaceAll(" ", "")));

                        } catch (Exception e) {
                            System.out.println("row : " + row + "  col +:" + colWhol);
                            resultBean.getData().add(barcode);

                        }
                    }
                    if(colRef!=-1){
                        try{  barcode.setReferenceRetailPrice(Double.valueOf(sheet.getCell(colRef, row).getContents().replaceAll(" ", "")));
                        }catch (Exception e){
                            resultBean.getData().add(barcode);
                            System.out.println("row : "+row+"  col +:"+ colRef);
                            System.out.println("barcodeNum : "+barcodeNum);
                            System.out.println(sheet.getCell(colRef, row).getContents());
                        }
                    }
                    if(colspec!=-1){
                        try{
                            barcode.setSpecifications(sheet.getCell(colspec, row).getContents());
                        }catch (Exception e){
                            System.out.println("row : "+row+"  col +:"+ colspec);
                            resultBean.getData().add(barcode);
                        }
                    }
                    BarcodeServiceImp.saveBarcode(barcode);
                }
            }
        }
        workbook.close();
        resultBean.setSuccess(1);
        resultBean.setMessage("��ȡExcel�ɹ� ,���±��û��¼��ɹ�");
        return resultBean;
    }
}
