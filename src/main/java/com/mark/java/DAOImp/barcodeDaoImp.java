package com.mark.java.DAOImp;

import com.mark.java.DAO.barcodeDao;
import com.mark.java.entity.barcode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vcc on 2017/3/22.
 */
@Component
public class barcodeDaoImp implements barcodeDao {
    @Autowired
    private SessionFactory sessionFactory;

    private String tableName="barcode";

    public int save(barcode barcode) {
         sessionFactory.getCurrentSession().saveOrUpdate(barcode);
        return 1;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public HashMap<String,Object> findBarcode(int pagenum, int pagesize) {
        String hql="from "+tableName;
        HashMap<String,Object> resultMap=new HashMap<String, Object>();
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        Query Countquery=sessionFactory.getCurrentSession().createQuery("select count(*) "+hql);
        resultMap.put("totalNum",((Long)Countquery.uniqueResult()).intValue());
        query.setFirstResult((pagenum-1)*pagesize);
        query.setMaxResults(pagesize);
        List<barcode> result=null;
        result= query.list();
        resultMap.put("barcodeList",result);
        return resultMap;
    }

    public barcode getBarcodeById(int id) {
        barcode result=null;
        result=(barcode)sessionFactory.getCurrentSession().get(barcode.class,id);
        return result;
    }

    public barcode getBarcodeByCodeNum(String barcodeNum) {
        if(barcodeNum==null){
            barcodeNum="";
        }
        String hql="from "+tableName +" a where a.barcodeNum = ?";
        Query query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,barcodeNum);
        List<barcode> result=null;
        result= query.list();
        if(result==null||result.size()==0){
            return null;
        }else{
            return result.get(0);
        }
    }

    public boolean editBarcode(barcode barcode) {
        if (barcode != null) {
            sessionFactory.getCurrentSession().update(barcode);
            return true;
        }
        return false;
    }

    public boolean delBarcode(barcode barcode) {
        if(barcode.getId()>0)
        {
            sessionFactory.getCurrentSession().delete(barcode);
            return true;

        }
        return false;
    }

    public boolean delBarcode(int id) {
        barcode barcode=(barcode) sessionFactory.getCurrentSession().get(barcode.class,id);
        if(barcode!=null){
            sessionFactory.getCurrentSession().delete(barcode);
            return true;
        }
        return false;

    }
    
}
