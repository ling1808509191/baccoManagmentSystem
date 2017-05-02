package com.mark.java.DAO;






import com.mark.java.entity.Law;
import com.mark.java.entity.department;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface LawDao {
    public int save(Law Law);
    public HashMap<String,Object> findLaw(int pagenum, int pagesize);
    public boolean editLaw(Law Law);
    public int getLawNumberByCategory(int id);
    public HashMap<String,Object> getPublishedLaw(int pagenum,int pagesize);
    public List<Law> getLawByCategoryId(int categoryId);
    public Law getLawById(int id);
    public boolean delLaw(Law Law);
    public boolean delLaw(int id);
}
