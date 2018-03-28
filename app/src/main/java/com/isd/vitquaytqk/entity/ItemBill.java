package com.isd.vitquaytqk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 11/25/2017.
 */

public class ItemBill implements Serializable{
    private String date;
    private String nameStaff;
    private int num;
    private List<Food>foods;

    public ItemBill(String date, String nameStaff, int num, List<Food> foods) {
        this.date = date;
        this.nameStaff = nameStaff;
        this.num = num;
        this.foods = foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
