package com.isd.vitquaytqk.entity;

/**
 * Created by Admin on 11/6/2017.
 */

public class SanPham {
    private String productCode;
    private String productName;
    private int price;
    private double qty;
    private boolean isView;
    private boolean note = true;
    private boolean type = false;
    private int productID;
    private String foodNameInBill;
    private boolean isSelect = false;
    private double num;
    private int noteString;

    public SanPham(String productCode, String productName, int price, double qty, boolean isView, String name, boolean isSelect, double num, int noteString) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.isView = isView;
        this.foodNameInBill = name;
        this.isSelect = isSelect;
        this.num = num;
        this.noteString = noteString;
    }

    public SanPham(String productCode, String productName, int price, double qty, boolean isView, boolean note, String name, boolean isSelect, double num, int noteString) {
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.isView = isView;
        this.note = note;
        this.foodNameInBill = name;
        this.num = num;
        this.isSelect = isSelect;
        this.noteString = noteString;
    }

    public int getNoteString() {
        return noteString;
    }

    public void setNoteString(int noteString) {
        this.noteString = noteString;
    }

    public String getFoodNameInBill() {
        return foodNameInBill;
    }

    public void setFoodNameInBill(String foodNameInBill) {
        this.foodNameInBill = foodNameInBill;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }



    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public boolean isNote() {
        return note;
    }

    public void setNote(boolean note) {
        this.note = note;
    }
}
