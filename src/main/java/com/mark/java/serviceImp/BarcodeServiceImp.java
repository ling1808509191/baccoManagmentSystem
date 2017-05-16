package com.mark.java.serviceImp;

import com.mark.java.DAOImp.barcodeDaoImp;
import com.mark.java.entity.barcode;
import com.mark.java.service.barcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vcc on 2017/5/16.
 */
@Service
@Transactional
public class BarcodeServiceImp implements barcodeService {
    @Autowired
    private barcodeDaoImp barcodeDaoImp;

    public com.mark.java.DAOImp.barcodeDaoImp getBarcodeDaoImp() {
        return barcodeDaoImp;
    }

    public void setBarcodeDaoImp(com.mark.java.DAOImp.barcodeDaoImp barcodeDaoImp) {
        this.barcodeDaoImp = barcodeDaoImp;
    }

    public boolean saveBarcode(barcode barcode) {
        return barcodeDaoImp.save(barcode)>0;
    }

    public barcode getbarcodeBybarcodeNum(String barocdeNum) {
        return barcodeDaoImp.getBarcodeByCodeNum(barocdeNum);
    }
}
