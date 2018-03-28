package com.isd.vitquaytqk.untils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Admin on 11/21/2017.
 */

public class Untils {
    public static final String ACCESS_TOKEN = "f123a1OyRZJ7vF8g3XOz6Yt";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    private List<String> users;

    public static String convertToVnd(double money) {
        NumberFormat formatter = new DecimalFormat("###,###");
        String resp = formatter.format(money);
        resp = resp.replaceAll(",", ".");
        return resp;
    }

}
