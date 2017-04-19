package com.mark.java.DAO;
import com.mark.java.entity.staffInfo;
import com.mark.java.entity.tobacco;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface tobaccoDao {
    public int save(tobacco tobacco);
    public List<tobacco> findTobacco(int pagenum, int pagesize);
    public List<tobacco> getTobaccosListByCaseNum(String num);
    public boolean editTobacco(tobacco tobacco);
    public tobacco getTobaccoById(int id);
    public boolean delTobacco(tobacco tobacco);
    public boolean delTobacco(int id);
}
