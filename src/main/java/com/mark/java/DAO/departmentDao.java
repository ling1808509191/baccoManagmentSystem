package com.mark.java.DAO;






import com.mark.java.entity.caseInfo;
import com.mark.java.entity.department;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface departmentDao {
    public int save(department account);
    public HashMap<String,Object> findDepartment(int pagenum, int pagesize);
    public List<department> findDepartment();
    public int getDepartmentTotalNum ();
    public boolean editDepartment(department department);
    public department getDepartmentById(int id);
    public department getDepartmentByName(String name);
    public List<department> searchDepartment(String name,String phone);
    public boolean delDepartment(department department);
    public boolean delDepartment(int id);
}
