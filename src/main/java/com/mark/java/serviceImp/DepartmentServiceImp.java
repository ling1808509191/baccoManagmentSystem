package com.mark.java.serviceImp;

import com.mark.java.DAO.AccountDao;
import com.mark.java.DAOImp.DepartmentDaoImp;
import com.mark.java.DAOImp.StaffInfoDaoImp;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.department;
import com.mark.java.service.departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/27.
 */
@Service
@Transactional
public class DepartmentServiceImp implements departmentService {
    @Autowired
    private com.mark.java.DAOImp.AccountDAOImp AccountDAOImp;

    @Autowired
    private DepartmentDaoImp departmentDaoImp;

    @Autowired
    private StaffInfoDaoImp staffInfoDaoImp;
    public resultBean addDepartment(String name, String description, String phone) {
        resultBean resultBean=new resultBean();
        department tempDepartment=departmentDaoImp.getDepartmentByName(name);
        if(tempDepartment!=null){
            resultBean.setSuccess(0);
            resultBean.setMessage("department name is already exited");
            return resultBean;
        }else{
            tempDepartment=new department();
        }
        tempDepartment.setName(name);
        tempDepartment.setDescription(description);
        tempDepartment.setPhone(phone);
        int departmentId=departmentDaoImp.save(tempDepartment);
        department department=departmentDaoImp.getDepartmentById(departmentId);
        if(department==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("add department error £¨can not save department£©");
            return resultBean;
        }else{
            resultBean.setSuccess(1);
            resultBean.setMessage("add department sucess");
            resultBean.getData().add(department);
            return resultBean;

        }
    }

    public resultBean getAllDepartments(int pagenum, int pagesize) {
        resultBean resultBean=new resultBean();
        HashMap<String,Object> resultList=departmentDaoImp.findDepartment(pagenum,pagesize);
        resultBean.setSuccess(1);
        resultBean.setMessage("search sucess");
        resultBean.getData().add(resultList);
        return resultBean;
    }

    public resultBean getDepartmentById(int departmentId) {
        resultBean resultBean=new resultBean();
        department department=departmentDaoImp.getDepartmentById(departmentId);
        if(department!=null){
            resultBean.setSuccess(1);
            resultBean.setMessage("search sucess");
            resultBean.getData().add(department);
            return resultBean;
        }else{
            resultBean.setSuccess(0);
            resultBean.setMessage("search faild");

            return resultBean;
        }

    }

    public resultBean searchDepartment(String name, String phone) {
        resultBean resultBean=new resultBean();
        List<department> resultList=departmentDaoImp.searchDepartment(name,phone);
        resultBean.setSuccess(1);
        resultBean.setMessage("searching sucess");
        resultBean.getData().add(resultList);
        return resultBean;
    }

    public resultBean editDepartment(int departmentId, String name, String phone, String description) {
       resultBean resultBean=new resultBean();
        department department=departmentDaoImp.getDepartmentById(departmentId);
        if(department==null){
            resultBean.setSuccess(0);
            resultBean.setMessage("wrong departmentID");
            return resultBean;
        }
        else{
            if(name!=null){
                department.setName(name);
            }
            if(phone!=null){
                department.setPhone(phone);
            }
            if(description!=null){
                department.setDescription(description);
            }
            if(departmentDaoImp.editDepartment(department)){
                resultBean.setSuccess(0);
                resultBean.setMessage("update department sucess");
                return resultBean;
            }
            resultBean.setSuccess(1);
            resultBean.setMessage("update department sucess");
        }
        return  resultBean;
    }

    public AccountDao getAccountDAOImp() {
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
}
