package com.isd.vitquaytqk.entity;

import java.io.Serializable;

/**
 * Created by Admin on 11/27/2017.
 */

public class OrderDetailsViewModel implements Serializable{
    private int ProductId;
    private double Quantity;
    private int TypeId;

    public OrderDetailsViewModel(int ProductId, double quantity, int typeId) {
        this.ProductId = ProductId;
        Quantity = quantity;
        TypeId = typeId;
    }

    public int getProductCode() {
        return ProductId;
    }

    public void setProductCode(int ProductId) {
        ProductId = ProductId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        TypeId = typeId;
    }
}
