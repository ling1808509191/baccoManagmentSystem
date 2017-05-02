package com.mark.java.DAO;






import com.mark.java.entity.LawCategory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface LawCategoryDao {
    public int save(LawCategory LawCategory);
    public HashMap<String,Object> findLawCategory(int pagenum, int pagesize);
    public LawCategory getLawCategoryByName(String name);

    public boolean editLawCategory(LawCategory LawCategory);
    public LawCategory getLawCategoryById(int id);
    public boolean delLawCategory(LawCategory LawCategory);
    public boolean delLawCategory(int id);
}
