package com.isd.vitquaytqk.entity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.isd.vitquaytqk.Interface.CheckLogin;
import com.isd.vitquaytqk.Interface.IReturnItemBill;
import com.isd.vitquaytqk.Interface.UserSS;
import com.isd.vitquaytqk.untils.Untils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/21/2017.
 */

public class DownloadData {

    public static Context ctx;
    private static DownloadData INSTANCE;
    public List<User> userMain;
    UserSS userSS;
    private IReturnItemBill iReturnItemBill;
    CheckLogin checkLogin;
    private List<SanPham> sanphams;
    private ReturnItemBill returnItem;

    public static DownloadData getInstance(Context ctx) {
        DownloadData.ctx = ctx;
        return (INSTANCE == null) ? new DownloadData() : INSTANCE;
    }

    public void checkLogin(Context context, String token, int userId, String password, Context contex) {

        SharedPreferences share = contex.getSharedPreferences("LinkSetting", Context.MODE_PRIVATE);
        String host = share.getString("LinkSelected", "");
        Log.d("host", host);
        String path = "http://" + host + "/Auth/Login?userId=" + userId + "&password=" + password + "&accessToken=" + Untils.ACCESS_TOKEN;
        checkLogin = (CheckLogin) context;

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                Boolean result = false;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);

                    String line = "";

                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        result = jsonObject.getBoolean("Result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                checkLogin.checkLogin(aBoolean);
            }
        }.execute(path);

    }

    public void exeLoadUsers(final Context context, String token) {

        SharedPreferences share = context.getSharedPreferences("LinkSetting", Context.MODE_PRIVATE);
        String host = share.getString("LinkSelected", "");
        final String path = "http://" + host + "/Auth/AllUser?accessToken=" + Untils.ACCESS_TOKEN;

        Log.d("link", path);

        final UserSS userSS = (UserSS) context;
        boolean running = false;

        new AsyncTask<String, Void, List<User>>() {
            public ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(context, "Vui lòng đợi...", "Đang kiểm tra..", false, true);
                pd.setCanceledOnTouchOutside(false);
            }

            @Override
            protected List<User> doInBackground(String... strings) {

                List<User> users = new ArrayList<>();
                StringBuilder stringBuilder;

                try {

                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10000);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    stringBuilder = new StringBuilder();

                    String line = "";
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }

                    JSONArray array = new JSONArray(stringBuilder.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        int UserId = obj.getInt("UserId");
                        String UserName = obj.getString("UserName");
                        users.add(new User(UserId, UserName));
                    }

                } catch (Exception e) {
                    Log.d("errors", e.toString());
                    return null;
                }
                return users;

            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                userSS.setSpinner(users);
                pd.dismiss();
            }
        }.execute(path);
    }

    public void exeDownload(String path, final List<SanPham> sanphams, final Context context) {

        iReturnItemBill = (IReturnItemBill)context;
        new AsyncTask<Void, Void, ReturnItemBill>() {
            private ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(context, "Vui lòng đợi...", "Đang kiểm tra..", false, true);
                pd.setCanceledOnTouchOutside(false);
            }

            @Override
            protected ReturnItemBill doInBackground(Void... voids) {

                ReturnItemBill returnItemBill;

                //viewModel
                List<OrderDetailsViewModel> products = new ArrayList<>();
                for (int i = 0; i < sanphams.size(); i++) {
                    OrderDetailsViewModel model = new OrderDetailsViewModel(sanphams.get(i).getProductID(), sanphams.get(i).getNum(), sanphams.get(i).getNoteString());
                    products.add(model);
                }
                Log.d("sanpham", products.size() + "");
                //userId
                SharedPreferences share = ctx.getSharedPreferences("user", Context.MODE_PRIVATE);
                int userId = share.getInt("user_id", -1);
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    JSONObject postDataParams = new JSONObject();
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < products.size(); i++) {
                        JSONObject item = new JSONObject();
                        item.put("ProductId", products.get(i).getProductCode());
                        item.put("Quantity", products.get(i).getQuantity());
                        item.put("TypeId", products.get(i).getTypeId());
                        array.put(item);
                    }
                    postDataParams.put("ViewModel", array);
                    postDataParams.put("masterNote", "");
                    postDataParams.put("AccessToken", Untils.ACCESS_TOKEN);
                    postDataParams.put("userId", userId);
                    postDataParams.put("waitMinutes", 0);

                    URL url = null;
                    //get link
                    SharedPreferences shareLink = ctx.getSharedPreferences("LinkSetting", Context.MODE_PRIVATE);
                    String linkSelected = shareLink.getString("LinkSelected", "");

                    url = new URL("http://" + linkSelected + "/order/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    //Log.d("log456", postDataParams.toString());
                    writer.write(postDataParams.toString());

                    writer.flush();
                    writer.close();
                    os.close();

                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = "";

                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    is.close();
                    conn.disconnect();

                    //doc json
                    JSONObject json = new JSONObject(stringBuilder.toString());
                    boolean success = json.getBoolean("Success");
                    if(success) {

                        JSONObject jsonObject = json.getJSONObject("Data");
                        String EmployeeName = jsonObject.getString("EmployeeName");
                        String BookingTime = jsonObject.getString("BookingTime");
                        double TotalPrice = jsonObject.getDouble("TotalPrice");
                        String OrderNumber = jsonObject.getString("OrderNumber");
                        String Note = jsonObject.getString("Note");


                        JSONArray jsonArray = jsonObject.getJSONArray("OrderDetailsViewModel");
                        List<ProductReturnBill> productReturnBills = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectArr = jsonArray.getJSONObject(i);
                            int ProductId = jsonObjectArr.getInt("ProductId");
                            String ProductName = jsonObjectArr.getString("ProductName");
                            double UnitPrice = jsonObjectArr.getDouble("UnitPrice");
                            double Price = jsonObjectArr.getDouble("Price");
                            int TypeId = jsonObjectArr.getInt("TypeId");
                            double Quantity = jsonObjectArr.getDouble("Quantity");
                            productReturnBills.add(new ProductReturnBill(ProductId, ProductName, Price, UnitPrice, "", TypeId, Quantity));
                        }
                        returnItemBill = new ReturnItemBill(success, EmployeeName, BookingTime, TotalPrice, OrderNumber, "", productReturnBills);
                    }else{

                        returnItemBill = new ReturnItemBill(success,"","",0,"","",null);
                    }

                    Log.d("datajson", stringBuilder.toString());

                } catch (Exception ex){
                    Log.d("ex12345",ex.toString());
                    return null;
                }
                return returnItemBill;
            }

            @Override
            protected void onPostExecute(ReturnItemBill rt) {
                super.onPostExecute(rt);
                iReturnItemBill.setReturnItemBill(rt);
                pd.dismiss();
            }
        }.execute();
    }

}


