package com.mark.java.entity;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/17.
 */
@Entity
@Table
/*
烟草表
 */
public class tobacco {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = caseInfo.class)
    @JoinColumn(name = "m_id",referencedColumnName = "id")
    private caseInfo mCase_info;
    @ManyToOne(targetEntity = barcode.class)
    @JoinColumn(name = "b_id",referencedColumnName = "id")
    private barcode barcode;
    private String laserCodeNum;
    private String laserCodeUrl;
    /*
        卷烟外观照片链接，以;分隔
         */
    private String Imagurls;
    public com.mark.java.entity.barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(com.mark.java.entity.barcode barcode) {
        this.barcode = barcode;
    }

    public String getImagurls() {
        return Imagurls;
    }

    public void setImagurls(String imagurls) {
        Imagurls = imagurls;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public caseInfo getmCase_info() {
        return mCase_info;
    }

    public void setmCase_info(caseInfo mCase_info) {
        this.mCase_info = mCase_info;
    }

    public String getLaserCodeNum() {
        return laserCodeNum;
    }

    public void setLaserCodeNum(String laserCodeNum) {
        this.laserCodeNum = laserCodeNum;
    }

    public String getLaserCodeUrl() {
        return laserCodeUrl;
    }

    public void setLaserCodeUrl(String laserCodeUrl) {
        this.laserCodeUrl = laserCodeUrl;
    }
}
