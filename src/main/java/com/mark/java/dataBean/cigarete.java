package com.mark.java.dataBean;

/**
 * Created by vcc on 2017/4/9.
 */
public class cigarete {
    private String name;
    private Integer price;
    //条形码
    private String barcode;
    //激光码照片
    private String laserCodeImg;
    private String image1;
    private String image2;
    //激光码内容
    private String laserCodeNum;

    public String getLaserCodeImg() {
        return laserCodeImg;
    }

    public void setLaserCodeImg(String laserCodeImg) {
        this.laserCodeImg = laserCodeImg;
    }

    public String getLaserCodeNum() {
        return laserCodeNum;
    }

    public void setLaserCodeNum(String laserCodeNum) {
        this.laserCodeNum = laserCodeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
