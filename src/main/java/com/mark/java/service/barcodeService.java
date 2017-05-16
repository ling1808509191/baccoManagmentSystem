package com.mark.java.service;

import com.mark.java.entity.barcode;

/**
 * Created by vcc on 2017/5/16.
 */
public interface barcodeService {
    public boolean saveBarcode(barcode barcode);
    public barcode getbarcodeBybarcodeNum(String barocdeNum);
}
