package com.mark.java.entity;

import javax.persistence.*;

/**
 * Created by vcc on 2017/3/17.
 */
@Entity
@Table
/*
条形码信息
 */
public class barcode {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String barcodeNum;
    private String name;
    private String specifications;
    /*
    建议零售价
     */
    private double referenceRetailPrice;
    /*批发价价
     */
    private double wholesalesPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcodeNum() {
        return barcodeNum;
    }

    public void setBarcodeNum(String barcodeNum) {
        this.barcodeNum = barcodeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public double getReferenceRetailPrice() {
        return referenceRetailPrice;
    }

    public void setReferenceRetailPrice(double referenceRetailPrice) {
        this.referenceRetailPrice = referenceRetailPrice;
    }

    public double getWholesalesPrice() {
        return wholesalesPrice;
    }

    public void setWholesalesPrice(double wholesalesPrice) {
        this.wholesalesPrice = wholesalesPrice;
    }
}
