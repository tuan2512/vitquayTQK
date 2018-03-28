package com.isd.vitquaytqk.Model;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Admin on 11/24/2017.
 */

public class MainItem {
    private Button btnMain;
    private int position;
    private TextView tvMain;
    private Spinner spnMain;

    public MainItem(Button btnMain, int position, TextView tvMain, Spinner spnMain) {
        this.btnMain = btnMain;
        this.position = position;
        this.tvMain = tvMain;
        this.spnMain = spnMain;
    }

    public Button getBtnMain() {
        return btnMain;
    }

    public void setBtnMain(Button btnMain) {
        this.btnMain = btnMain;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TextView getTvMain() {
        return tvMain;
    }

    public void setTvMain(TextView tvMain) {
        this.tvMain = tvMain;
    }

    public Spinner getSpnMain() {
        return spnMain;
    }

    public void setSpnMain(Spinner spnMain) {
        this.spnMain = spnMain;
    }
}
