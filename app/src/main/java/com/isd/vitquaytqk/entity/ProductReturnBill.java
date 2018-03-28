package com.isd.vitquaytqk.entity;

/**
 * Created by Admin on 11/28/2017.
 */

public class ProductReturnBill {
    private int productId;
    private String productName;
    private double productPrice;
    private double totalMoney;
    private String note;
    private int typeId;
    private double quantity;

    public ProductReturnBill(int productId, String productName, double productPrice, double totalMoney, String note, int typeId, double quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalMoney = totalMoney;
        this.note = note;
        this.typeId = typeId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
