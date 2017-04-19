package com.mark.java.service;

import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.department;

/**
 * Created by vcc on 2017/3/23.
 */
public interface departmentService {
    public resultBean addDepartment(String name, String description, String phone);
    public resultBean getAllDepartments(int pagenum,int pagesize);
    public resultBean getDepartmentById(int departmentId);
    public resultBean searchDepartment(String name,String phone);
    public resultBean editDepartment(int departmentId, String name,String phone,String description);

}
