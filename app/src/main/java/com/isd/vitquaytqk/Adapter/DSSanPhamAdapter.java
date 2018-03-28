package com.isd.vitquaytqk.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.isd.vitquaytqk.Interface.TotalMoneyImp;
import com.isd.vitquaytqk.Model.MainItem;
import com.isd.vitquaytqk.entity.SanPham;
import com.isd.vitquaytqk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/6/2017.
 */

public class DSSanPhamAdapter extends RecyclerView.Adapter<DSSanPhamAdapter.Holder> {
    Context context;
    List<SanPham> listSanPham;
    double thanhToan;
    TotalMoneyImp thanhToan2;
    TextView textVQ, textVQDG, textXX, textSN, textTQ;
    Button buttonVQ, buttonVQDG, buttonXX, buttonSN, buttonTQ;
    Double a = 0d;
    private ImageView imgPlus, imgPlus1;
    private ImageView imgMinus, imgMinus1;
    private EditText edtNum;
    private Button btnCannel;
    private Button btnSave;
    Dialog dialog;
    private Holder holder;
    private TextView txtGlobal;
    private EditText edtNumGram;
    private EditText edtMoney;
    private Button btnCannelGram;
    private Button btnSaveGram;
    private SanPham sanPham;
    private int positionGlobal;
    private Spinner spinnerGlobal;
    private List<MainItem> mainItems;
    private List<TextView> tvItems;
    private Button btnGlobal;

    public DSSanPhamAdapter(Context context, List<SanPham> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
        this.thanhToan2 = (TotalMoneyImp) context;
        this.mainItems = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.item_home_recyclerview, parent, false);
        // Holder: nắm giữ
        holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.d("string", position + "");
        SanPham sanPham = listSanPham.get(position);

        holder.btnSanPham.setText(listSanPham.get(position).getProductName());

        // spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, new String[]{"", "KC", "C2"});
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        holder.spinner.setAdapter(dataAdapter);

        // gán thành phần của item ở vị trí(1,4,7,10,13) vào mainItems
        if (position == 1 || position == 4 || position == 7 || position == 10 || position == 13) {
            mainItems.add(new MainItem(holder.btnSanPham, position, holder.txtQty, holder.spinner));
        }

        //visible spinner
        if (!sanPham.isNote()) {
            holder.spinner.setVisibility(View.INVISIBLE);
        }

        //visible textview
        if (sanPham.isView() == false) {
            holder.txtQty.setVisibility(View.INVISIBLE);
        }
    }

    public List<SanPham> getListSanPham() {
        return this.listSanPham;
    }
    public void setRemoveSelected(){
        for (int i = 0 ; i < this.listSanPham.size(); i++)
            this.listSanPham.get(i).setSelect(false);
    }

    // hàm tính cho 2 nút cộng trừ ở dialog
    private double plusMinus(String math, double valueStart, double value) {
        switch (math) {
            // cộng trừ 1
            case "plus":
                return valueStart + value;
            case "minus":
                return valueStart - value;
            // cộng trừ 0.5
            case "plus1":
                return valueStart + value;
            case "minus1":
                return valueStart - value;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {
        Button btnSanPham;
        TextView txtQty;
        Spinner spinner;

        public Holder(View itemView) {
            super(itemView);
            btnSanPham = (Button) itemView.findViewById(R.id.btn_SanPham);
            txtQty = (TextView) itemView.findViewById(R.id.txt_Qty);
            spinner = (Spinner) itemView.findViewById(R.id.spn);

            // sự kiện cho spinner
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    final SanPham sanPham = listSanPham.get(getAdapterPosition());

                    // set ghi chú cho sản phẩm được chọn
                    switch (sanPham.getProductCode()) {
                        case "VQDG":
                            switch ((String) mainItems.get(3).getSpnMain().getSelectedItem()) {
                                case "":
                                    listSanPham.get(10).setNoteString(0);
                                    break;
                                case "KC":
                                    listSanPham.get(10).setNoteString(1);
                                    break;
                                case "C2":
                                    listSanPham.get(10).setNoteString(2);
                                    break;
                            }
                            break;
                        case "VQ":
                            switch ((String) mainItems.get(4).getSpnMain().getSelectedItem()) {
                                case "":
                                    listSanPham.get(13).setNoteString(0);
                                    break;
                                case "KC":
                                    listSanPham.get(13).setNoteString(1);
                                    break;
                                case "C2":
                                    listSanPham.get(13).setNoteString(2);
                                    break;
                            }
                            break;
                        default:
                            switch ((String) spinner.getSelectedItem()) {
                                case "":
                                    sanPham.setNoteString(0);
                                    break;
                                case "KC":
                                    sanPham.setNoteString(1);
                                    break;
                                case "C2":
                                    sanPham.setNoteString(2);
                                    break;
                            }
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnSanPham.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final SanPham sanPham = listSanPham.get(getAdapterPosition());

                    btnSanPham.setBackgroundResource(R.drawable.button_sanpham);

                    switch (sanPham.getProductCode()) {
                        case "TQ":
                            if (!sanPham.isView()) {
                                mainItems.get(0).getBtnMain().setBackgroundResource(R.drawable.button_sanpham);
                            }
                            if (mainItems.get(0).getTvMain().getText().toString().equals(""))
                                mainItems.get(0).getTvMain().setText("0");
                            double endTQValue = sanPham.getQty();
                            mainItems.get(0).getTvMain().setText((Double.parseDouble(mainItems.get(0).getTvMain().getText().toString()) + endTQValue + "").replace(".0", ""));
                            listSanPham.get(1).setSelect(true);
                            listSanPham.get(1).setNum(Double.parseDouble(mainItems.get(0).getTvMain().getText().toString()));
                            break;
                        case "SN":
                            if (!sanPham.isView()) {
                                mainItems.get(1).getBtnMain().setBackgroundResource(R.drawable.button_sanpham);
                            }

                            if (mainItems.get(1).getTvMain().getText().toString().equals(""))
                                mainItems.get(1).getTvMain().setText("0");
                            double endSNValue = sanPham.getQty();
                            mainItems.get(1).getTvMain().setText((Double.parseDouble(mainItems.get(1).getTvMain().getText().toString()) + endSNValue + "").replace(".0", ""));
                            listSanPham.get(4).setSelect(true);
                            listSanPham.get(4).setNum(Double.parseDouble(mainItems.get(1).getTvMain().getText().toString()));
                            break;
                        case "XX":
                            if (!sanPham.isView()) {
                                mainItems.get(2).getBtnMain().setBackgroundResource(R.drawable.button_sanpham);
                            }
                            if (mainItems.get(2).getTvMain().getText().toString().equals(""))
                                mainItems.get(2).getTvMain().setText("0");
                            double endXXValue = sanPham.getQty();
                            mainItems.get(2).getTvMain().setText((Double.parseDouble(mainItems.get(2).getTvMain().getText().toString()) + endXXValue + "").replace(".0", ""));
                            listSanPham.get(7).setSelect(true);
                            listSanPham.get(7).setNum(Double.parseDouble(mainItems.get(2).getTvMain().getText().toString()));
                            break;
                        case "VQDG":
                            if (!sanPham.isView()) {
                                mainItems.get(3).getBtnMain().setBackgroundResource(R.drawable.button_sanpham);
                            }
                            if (mainItems.get(3).getTvMain().getText().toString().equals(""))
                                mainItems.get(3).getTvMain().setText("0");
                            double endVQDGValue = sanPham.getQty();
                            mainItems.get(3).getTvMain().setText((Double.parseDouble(mainItems.get(3).getTvMain().getText().toString()) + endVQDGValue + "").replace(".0", ""));
                            listSanPham.get(10).setSelect(true);
                            listSanPham.get(10).setNum(Double.parseDouble(mainItems.get(3).getTvMain().getText().toString()));
                            break;
                        case "VQ":
                            if (!sanPham.isView()) {
                                mainItems.get(4).getBtnMain().setBackgroundResource(R.drawable.button_sanpham);
                            }
                            if (mainItems.get(4).getTvMain().getText().toString().equals(""))
                                mainItems.get(4).getTvMain().setText("0");
                            double endVQValue = sanPham.getQty();
                            mainItems.get(4).getTvMain().setText((Double.parseDouble(mainItems.get(4).getTvMain().getText().toString()) + endVQValue + "").replace(".0", ""));
                            listSanPham.get(13).setSelect(true);
                            listSanPham.get(13).setNum(Double.parseDouble(mainItems.get(4).getTvMain().getText().toString()));
                            break;
                        default:
                            if (txtQty.getText().equals(""))
                                txtQty.setText("0");
                            double endValue = sanPham.getQty();
                            txtQty.setText((endValue + Double.parseDouble(txtQty.getText().toString()) + "").replace(".0", ""));
                            sanPham.setSelect(true);
                            sanPham.setNum(Double.parseDouble(txtQty.getText().toString()));
                            break;
                    }
                    thanhToan2.ThanhToan(sanPham.getPrice() * sanPham.getQty());
                }
            });

            // onLongClick show dialog của từng item
            btnSanPham.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final SanPham sanPham = listSanPham.get(getAdapterPosition());

                    if (sanPham.isView()) {
                        if (!sanPham.isType()) {
                            // gán textView của item được chọn vào txtGlobal trước khi show dialog
                            txtGlobal = txtQty;
                            btnGlobal = btnSanPham;
                            dialog = new Dialog(context);
                            dialog.setContentView(R.layout.layout_dialog);

                            TextView txtTitle = dialog.findViewById(R.id.txt_Title);
                            imgPlus = dialog.findViewById(R.id.img_Plus);
                            imgMinus = dialog.findViewById(R.id.img_Minus);
                            imgPlus1 = dialog.findViewById(R.id.img_Plus1);
                            imgMinus1 = dialog.findViewById(R.id.img_Minus1);
                            edtNum = dialog.findViewById(R.id.edt_Number);
                            btnCannel = dialog.findViewById(R.id.btn_Cancel);
                            btnSave = dialog.findViewById(R.id.btn_Save);

                            edtNum.setText(txtGlobal.getText());

                            imgMinus.setOnClickListener(Holder.this);
                            imgPlus.setOnClickListener(Holder.this);
                            imgMinus1.setOnClickListener(Holder.this);
                            imgPlus1.setOnClickListener(Holder.this);

                            edtNum.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if (editable.toString().contains("-")) {
                                        edtNum.setText("");
                                    }
                                }
                            });

                            DSSanPhamAdapter.this.sanPham = sanPham;

                            btnCannel.setOnClickListener(Holder.this);
                            btnSave.setOnClickListener(Holder.this);

                            txtTitle.setText(sanPham.getProductName());

                            dialog.show();
                        } else {
                            txtGlobal = txtQty;
                            btnGlobal = btnSanPham;
                            dialog = new Dialog(context);
                            dialog.setContentView(R.layout.layout_dialog_gram);

                            TextView txtTitleGram = dialog.findViewById(R.id.txt_TitleGram);
                            edtNumGram = dialog.findViewById(R.id.edt_NumberGram);
                            edtMoney = dialog.findViewById(R.id.edt_Money);
                            btnCannelGram = dialog.findViewById(R.id.btn_CancelGram);
                            btnSaveGram = dialog.findViewById(R.id.btn_SaveGram);

                            DSSanPhamAdapter.this.sanPham = sanPham;

                            txtTitleGram.setText(sanPham.getProductName());

                            edtNumGram.addTextChangedListener(Holder.this);
                            edtMoney.addTextChangedListener(Holder.this);

                            btnSaveGram.setOnClickListener(Holder.this);
                            btnCannelGram.setOnClickListener(Holder.this);

                            dialog.show();
                        }
                    }
                    return true;
                }
            });

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // nút trừ 1
                case R.id.img_Minus:
                    String edtNu = edtNum.getText().toString();
                    if (edtNu.equals(""))
                        edtNu = "0";
                    double value = Double.parseDouble(edtNu);
                    if (value > 0) {
                        double result = plusMinus("minus", value, 1);
                        edtNum.setText((result + "").replace(".0", ""));
                    }
                    break;

                // nút cộng 1
                case R.id.img_Plus:
                    String edtNuPlus = edtNum.getText().toString();
                    if (edtNuPlus.equals(""))
                        edtNuPlus = "0";
                    double resultPlus = plusMinus("plus", Double.parseDouble(edtNuPlus), 1);
                    edtNum.setText((resultPlus + "").replace(".0", ""));
                    break;


                // nút trừ 0.5
                case R.id.img_Minus1:
                    String edtNuMinus = edtNum.getText().toString();
                    if (edtNuMinus.equals(""))
                        edtNuMinus = "0";
                    double value1 = Double.parseDouble(edtNuMinus);
                    if (value1 > 0) {
                        double result = plusMinus("minus", value1, 0.5);
                        edtNum.setText((result + "").replace(".0", ""));
                    }
                    break;

                // nút cộng 0.5
                case R.id.img_Plus1:
                    String edtNuPlus1 = edtNum.getText().toString();
                    if (edtNuPlus1.equals(""))
                        edtNuPlus1 = "0";
                    double resultPlus1 = (plusMinus("plus", Double.parseDouble(edtNuPlus1), 0.5));
//                    Log.d("text", result + "");
                    edtNum.setText((resultPlus1 + "").replace(".0", ""));
                    break;

                // nút Hủy
                case R.id.btn_Cancel:
                    dialog.dismiss();
                    break;

                // nút Lưu
                case R.id.btn_Save:
                    if (!txtGlobal.getText().toString().equals("") && !edtNum.getText().toString().equals("")) {
                        thanhToan2.ThanhToan((Double.parseDouble(edtNum.getText().toString()) - Double.parseDouble(txtGlobal.getText().toString())) * DSSanPhamAdapter.this.sanPham.getPrice());
                    } else if (txtGlobal.getText().toString().equals("") && !edtNum.getText().toString().equals("")) {
                        thanhToan2.ThanhToan(((Double.parseDouble(edtNum.getText().toString()) - 0)) * DSSanPhamAdapter.this.sanPham.getPrice());
                    } else if (edtNum.getText().toString().equals("") && !txtGlobal.getText().toString().equals("")) {
                        thanhToan2.ThanhToan((0 - Double.parseDouble(txtGlobal.getText().toString())) * DSSanPhamAdapter.this.sanPham.getPrice());
                    }

                    if (edtNum.getText().toString().length() > 1) {
                        if (edtNum.getText().toString().startsWith("0") && !edtNum.getText().toString().equals("0.5")) {
                            txtGlobal.setText(edtNum.getText().toString().substring(1));
                        } else {
                            txtGlobal.setText(edtNum.getText().toString());
                        }

                    } else {
                        txtGlobal.setText(edtNum.getText().toString());
                    }
                    if (edtNum.getText().toString().equals("") || edtNum.getText().toString().equals("0")) {
                        DSSanPhamAdapter.this.sanPham.setSelect(false);
                        btnGlobal.setBackgroundResource(android.R.drawable.btn_default);
                        txtGlobal.setText("");
                    }
                    dialog.dismiss();
                    break;

                // nút Lưu
                case R.id.btn_SaveGram:
                    if (!txtGlobal.getText().toString().equals("") && !edtNumGram.getText().toString().equals("")) {
                        thanhToan2.ThanhToan((Double.parseDouble(edtNumGram.getText().toString()) - Double.parseDouble(txtGlobal.getText().toString())) * DSSanPhamAdapter.this.sanPham.getPrice());
                    } else if (txtGlobal.getText().toString().equals("") && !edtNumGram.getText().toString().equals("")) {
                        thanhToan2.ThanhToan(((Double.parseDouble(edtNumGram.getText().toString()) - 0)) * DSSanPhamAdapter.this.sanPham.getPrice());
                    } else if (edtNumGram.getText().toString().equals("") && !txtGlobal.getText().toString().equals("")) {
                        thanhToan2.ThanhToan((0 - Double.parseDouble(txtGlobal.getText().toString())) * DSSanPhamAdapter.this.sanPham.getPrice());
                    }

                    txtGlobal.setText(edtNumGram.getText().toString());

                    if (edtNumGram.getText().toString().equals("") || edtNumGram.getText().toString().equals("0")) {
                        DSSanPhamAdapter.this.sanPham.setSelect(false);
                        btnGlobal.setBackgroundResource(android.R.drawable.btn_default);
                        txtGlobal.setText("");
                    }
                    dialog.dismiss();
                    break;

                // nút Hủy
                case R.id.btn_CancelGram:
                    dialog.dismiss();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        // chuyển đổi qua lại giữa số lượng và giá tiền
        // hasFocus kiểm tra xem edit nào đang được trỏ nếu k được trỏ thì j thực thi câu lệnh if
        @Override
        public void afterTextChanged(Editable editable) {
            if (editable == edtNumGram.getEditableText() && edtNumGram.hasFocus()) {
                try {
                    if (editable.toString().contains("-")) {
                        edtNumGram.setText("");
                        edtMoney.setText("");
                    }
                    double num = Double.parseDouble(editable.toString());
                    edtMoney.setText(((sanPham.getPrice() * num) + "").replace(".0", ""));
                } catch (Exception e) {
                    edtMoney.setText("");
                }
            }
            if (editable == edtMoney.getEditableText() && edtMoney.hasFocus()) {
                try {
                    if (editable.toString().contains("-")) {
                        edtNumGram.setText("");
                        edtMoney.setText("");
                    }
                    double num = Double.parseDouble(editable.toString());
                    edtNumGram.setText(((num / sanPham.getPrice()) + "").replace(".0", ""));
                } catch (Exception e) {
                    edtNumGram.setText("");
                }
            }
        }
    }
}
