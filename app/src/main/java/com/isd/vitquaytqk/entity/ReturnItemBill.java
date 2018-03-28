package com.isd.vitquaytqk.entity;

import java.util.List;

/**
 * Created by Admin on 11/25/2017.
 */

public class ReturnItemBill {
    private boolean success;
    private String employeeName;
    private String bookingTime;
    private double totalMoney;
    private String orderNumber;
    private String note;
    private List<ProductReturnBill> productReturnBills;

    public ReturnItemBill(boolean success, String employeeName, String bookingTime, double totalMoney, String orderNumber, String note, List<ProductReturnBill> productReturnBills) {
        this.success = success;
        this.employeeName = employeeName;
        this.bookingTime = bookingTime;
        this.totalMoney = totalMoney;
        this.orderNumber = orderNumber;
        this.note = note;
        this.productReturnBills = productReturnBills;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ProductReturnBill> getProductReturnBills() {
        return productReturnBills;
    }

    public void setProductReturnBills(List<ProductReturnBill> productReturnBills) {
        this.productReturnBills = productReturnBills;
    }
}
