package com.isd.vitquaytqk.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.isd.vitquaytqk.Interface.CheckLogin;
import com.isd.vitquaytqk.Interface.UserSS;
import com.isd.vitquaytqk.R;
import com.isd.vitquaytqk.entity.DownloadData;
import com.isd.vitquaytqk.entity.User;
import com.isd.vitquaytqk.untils.Untils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements UserSS, CheckLogin {

    private DownloadData downloadData;
    private Spinner spnUserName;
    List<User> users;
    Button btnLogin;
    EditText edtPassWord;
    int userID;
    String userName;
    boolean printStyle;
    RadioButton rdbLink1, rdbLink2;
    private RadioButton rdbNow;
    private RadioButton rdbReview;
    private Dialog dialogPrint;
    private Button btnSave;
    private RadioGroup rdG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_setting);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtPassWord = (EditText) findViewById(R.id.edt_password);

        rdG = (RadioGroup) findViewById(R.id.rdG_link);
        rdbLink1 = (RadioButton) findViewById(R.id.rdb_link1);
        rdbLink2 = (RadioButton) findViewById(R.id.rdb_link2);

        dialogPrint = new Dialog(this);
        dialogPrint.setTitle("Cấu hình in");
        dialogPrint.setContentView(R.layout.dialog_printstyle);
        btnSave = (Button) dialogPrint.findViewById(R.id.btn_savePrintStyle);
        rdbNow = (RadioButton) dialogPrint.findViewById(R.id.rdb_printNow);
        rdbReview = (RadioButton) dialogPrint.findViewById(R.id.rdb_printReview);

        setSupportActionBar(toolbar);

        spnUserName = (Spinner) findViewById(R.id.spn_taikhoan);

        downloadData = DownloadData.getInstance(this);

        setRadioLink();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = spnUserName.getSelectedItemPosition();
                if (getUserFromPos(position) == null) {
                    userID = -1;
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Thông báo")
                            .setMessage("Không thể kết nối tới server")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    userName = getUserFromPos(position).getUserName();
                    userID = getUserFromPos(position).getUserID();
                    String password = edtPassWord.getText().toString();
                    downloadData.checkLogin(LoginActivity.this, Untils.ACCESS_TOKEN, userID, password, LoginActivity.this);
                }
            }
        });

        rdG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radio = (RadioButton) radioGroup.findViewById(i);
                boolean isCheck = radio.isChecked();
                if (isCheck) {
                    SharedPreferences share = getSharedPreferences("LinkSetting", MODE_PRIVATE);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("LinkSelected", radio.getText().toString());
                    editor.commit();
                    loadUser();
                }
            }
        });

        loadPrintDefault();

    }

    private void loadPrintDefault() {

        SharedPreferences share = getSharedPreferences("PrintSetting", MODE_PRIVATE);
        boolean ischeck = share.getBoolean("printStyle", true);
        if (ischeck)
            rdbNow.setChecked(true);
        else
            rdbReview.setChecked(true);

    }

    private void setRadioLink() {
        SharedPreferences share = getSharedPreferences("LinkSetting", MODE_PRIVATE);
        String link1 = share.getString("Link1", "");
        String link2 = share.getString("Link2", "");
        if (!link1.equals(""))
            rdbLink1.setText(link1);
        if (!link2.equals(""))
            rdbLink2.setText(link2);
    }

    private User getUserFromPos(int position) {
        if (users == null)
            return null;
        return users.get(position);
    }

    private void loadUser() {
        downloadData.exeLoadUsers(this, Untils.ACCESS_TOKEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_printStyle:


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rdbNow.isChecked()) {
                            printStyle = true;
                        }
                        if (rdbReview.isChecked()) {
                            printStyle = false;
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences("PrintSetting", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("printStyle", printStyle);
                        editor.commit();

                        dialogPrint.dismiss();
                    }
                });

                dialogPrint.show();
                break;
            case R.id.item_ip:
                final Dialog dialogIP = new Dialog(this);
                dialogIP.setTitle("Cấu hình ip");
                dialogIP.setContentView(R.layout.dialog_ip);
                final EditText edtLink1 = (EditText) dialogIP.findViewById(R.id.edt_link1);
                final EditText edtLink2 = (EditText) dialogIP.findViewById(R.id.edt_link2);
                Button btnSaveLink = (Button) dialogIP.findViewById(R.id.btn_saveLink);

                btnSaveLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtLink1.getText().toString().equals("") && edtLink2.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "Nội dung còn trống", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences("LinkSetting", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (!edtLink1.getText().toString().equals("")) {
                                rdbLink1.setText(edtLink1.getText().toString());
                                editor.putString("Link1", edtLink1.getText().toString());
                            }
                            if (!edtLink2.getText().toString().equals("")) {
                                rdbLink2.setText(edtLink2.getText().toString());
                                editor.putString("Link2", edtLink2.getText().toString());
                            }
                            editor.commit();
                        }
                        dialogIP.dismiss();
                    }
                });

                dialogIP.show();
                break;
        }
        return true;
    }

    @Override
    public void setSpinner(List<User> userss) {
        users = userss;
        List<String> usernames = new ArrayList<>();
        if (users != null) {
            for (User user : userss) {
                usernames.add(user.getUserName());
            }
            Log.d("arr", users.size() + "");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, usernames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnUserName.setAdapter(dataAdapter);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không thể kết nối tới server")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void checkLogin(boolean result) {
        if (result) {
            SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            editor.putInt("user_id", userID);
            editor.putString("user_name", userName);
            editor.commit();

            edtPassWord.setText("");
            Intent intent = new Intent(this, OrderActivity.class);
            Bundle bundle = new Bundle();
            boolean isCheck = (rdbLink1.isChecked()) ? true : false;
            String link = "";
            if (isCheck)
                link = rdbLink1.getText().toString();
            else
                link = rdbLink2.getText().toString();
            bundle.putString("link", link);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
