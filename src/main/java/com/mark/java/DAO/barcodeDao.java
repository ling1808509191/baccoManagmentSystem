package com.mark.java.DAO;




import com.mark.java.entity.barcode;

import java.util.List;

/**
 * Created by vcc on 2017/3/20.
 */
public interface barcodeDao {
    public int save(barcode account);
    public List<barcode> findBarcode(int pagenum, int pagesize);
    public barcode getBarcodeById(int id);
    public barcode getBarcodeByCodeNum(String barcodeNum);
    public boolean editBarcode(barcode barcode);
    public boolean delBarcode(barcode barcode);
    public boolean delBarcode(int id);
}
