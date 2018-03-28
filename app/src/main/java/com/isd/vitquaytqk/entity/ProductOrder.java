package com.isd.vitquaytqk.entity;

/**
 * Created by Admin on 11/28/2017.
 */

public class ProductOrder {
    private String productName;
    private String type;
    private double productNum;
    private double productPrice;
    private double productTotalMoney;

    public ProductOrder(String productName,int typeID,double productNum, double productPrice, double productTotalMoney) {
        this.productName = productName;
        this.productNum = productNum;
        this.productPrice = productPrice;
        this.productTotalMoney = productTotalMoney;
        switch (typeID){
            case 0:
                this.type = "";
                break;
            case 1:
                this.type = "KC";
                break;
            case 2:
                this.type = "C2";
                break;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductNum() {
        return productNum;
    }

    public void setProductNum(double productNum) {
        this.productNum = productNum;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductTotalMoney() {
        return productTotalMoney;
    }

    public void setProductTotalMoney(double productTotalMoney) {
        this.productTotalMoney = productTotalMoney;
    }
}
