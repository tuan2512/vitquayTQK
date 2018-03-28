package com.isd.vitquaytqk.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isd.vitquaytqk.Adapter.DSSanPhamAdapter;
import com.isd.vitquaytqk.Adapter.ProductOrderAdapter;
import com.isd.vitquaytqk.Interface.CheckProduct;
import com.isd.vitquaytqk.Interface.IReturnItemBill;
import com.isd.vitquaytqk.Interface.TotalMoneyImp;
import com.isd.vitquaytqk.R;
import com.isd.vitquaytqk.entity.DownloadData;
import com.isd.vitquaytqk.entity.ProductOrder;
import com.isd.vitquaytqk.entity.ProductReturnBill;
import com.isd.vitquaytqk.entity.ReturnItemBill;
import com.isd.vitquaytqk.entity.SanPham;
import com.isd.vitquaytqk.untils.Untils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class OrderActivity extends AppCompatActivity implements TotalMoneyImp, View.OnClickListener, CheckProduct, IReturnItemBill {
    List<SanPham> listSanPham;
    RecyclerView recyclerView;
    DSSanPhamAdapter dsSanPhamAdapter;
    TextView txtTotal;
    TotalMoneyImp thanhToan1;
    double Tong = 0;
    Button btnPay;
    private Canvas canvas;
    private String link;
    private IWoyouService woyouService;
    private Bitmap bitmap;
    private int index = 0;
    private List<SanPham> sanPhamSelecteds;
    private ReturnItemBill returnItem;
    private boolean print = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Home);
        txtTotal = (TextView) findViewById(R.id.txt_Total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        btnPay = (Button) findViewById(R.id.btn_Pay);
        btnPay.setOnClickListener(this);

        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        String title = share.getString("user_name", "");
        setSupportActionBar(toolbar);
        // set tiêu đề cho actionbar
        getSupportActionBar().setTitle(title);

        SanPham sanPham = new SanPham("GL", "Gà Luộc", 320000, 1, true, "Gà Luộc", false, 0, 0);
        sanPham.setProductID(5);
        SanPham sanPham1 = new SanPham("TQ", "100g TQ", 25000, 1, true, false, "Thịt Quay", false, 0, 0);
        sanPham1.setType(true);
        sanPham1.setProductID(11);
        SanPham sanPham2 = new SanPham("TQ", "500g TQ", 25000, 5, false, false, "Thịt Quay", false, 0, 0);
        sanPham2.setType(true);
        sanPham2.setProductID(11);

        SanPham sanPham3 = new SanPham("NTD", "Ngỗng TD", 900000, 1, true, "Ngỗng Tiêu Đen", false, 0, 0);
        sanPham3.setProductID(8);
        SanPham sanPham4 = new SanPham("SN", "100g SN", 26000, 1, true, false, "Sườn Non", false, 0, 0);
        sanPham4.setType(true);
        sanPham4.setProductID(10);
        SanPham sanPham5 = new SanPham("SN", "500g SN", 26000, 5, false, false, "Sườn Non", false, 0, 0);
        sanPham5.setType(true);
        sanPham5.setProductID(10);

        SanPham sanPham6 = new SanPham("NHK", "Ngỗng HK", 900000, 1, true, "Ngỗng Hồng Kông", false, 0, 0);
        sanPham6.setProductID(7);
        SanPham sanPham7 = new SanPham("XX", "100g XX", 25000, 1, true, false, "Xá Xíu", false, 0, 0);
        sanPham7.setType(true);
        sanPham7.setProductID(9);
        SanPham sanPham8 = new SanPham("XX", "500g XX", 25000, 5, false, false, "Xá Xíu", false, 0, 0);
        sanPham8.setType(true);
        sanPham8.setProductID(9);

        SanPham sanPham9 = new SanPham("DV", "Đầu Vịt", 10000, 1, true, false, "Đầu Vịt", false, 0, 0);
        sanPham9.setProductID(2);
        SanPham sanPham10 = new SanPham("VQDG", "VQ DG", 400000, 1, true, "Vịt Quay Da Giòn", false, 0, 0);
        sanPham10.setProductID(4);
        SanPham sanPham11 = new SanPham("VQDG", "1/2 VQ DG", 400000, 0.5, false, false, "Vịt Quay Da Giòn", false, 0, 0);
        sanPham11.setProductID(4);

        SanPham sanPham12 = new SanPham("BB", "BB", 10000, 1, true, false, "Bánh Bao", false, 0, 0);
        sanPham12.setProductID(1);
        SanPham sanPham13 = new SanPham("VQ", "VQ", 350000, 1, true, "Vịt Quay", false, 0, 0);
        sanPham13.setProductID(3);
        SanPham sanPham14 = new SanPham("VQ", "1/2 VQ", 350000, 0.5, false, false, "Vịt Quay", false, 0, 0);
        sanPham14.setProductID(3);

        SanPham sanPham15 = new SanPham("GXD", "Gà XD", 340000, 1, true, "Gà Xì Dầu", false, 0, 0);
        sanPham15.setProductID(6);

        listSanPham = new ArrayList<>();
        listSanPham.add(0, sanPham);
        listSanPham.add(1, sanPham1);
        listSanPham.add(2, sanPham2);
        listSanPham.add(3, sanPham3);
        listSanPham.add(4, sanPham4);
        listSanPham.add(5, sanPham5);
        listSanPham.add(6, sanPham6);
        listSanPham.add(7, sanPham7);
        listSanPham.add(8, sanPham8);
        listSanPham.add(9, sanPham9);
        listSanPham.add(10, sanPham10);
        listSanPham.add(11, sanPham11);
        listSanPham.add(12, sanPham12);
        listSanPham.add(13, sanPham13);
        listSanPham.add(14, sanPham14);
        listSanPham.add(15, sanPham15);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // khởi tạo DSSanPhamAdapter
        dsSanPhamAdapter = new DSSanPhamAdapter(this, listSanPham);
        // truyền adapter vào recyclerView
        recyclerView.setAdapter(dsSanPhamAdapter);

        // connect tới sv in
        Binding();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dsSanPhamAdapter = new DSSanPhamAdapter(this, listSanPham);
        recyclerView.setAdapter(dsSanPhamAdapter);
        Tong = 0;
        txtTotal.setText("0");
    }

    // TextView tổng tiền
    @Override
    public void ThanhToan(double thanhToan) {
        double oldValue = Double.parseDouble(txtTotal.getText().toString());
        txtTotal.setText((thanhToan + oldValue + "").replace(".0", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // nút Hủy restart lại danh sách sản phẩm
            // set TextView tổng tiền bằng 0
            case R.id.item_huy:
                dsSanPhamAdapter = new DSSanPhamAdapter(this, listSanPham);
                recyclerView.setAdapter(dsSanPhamAdapter);
                Tong = 0;
                txtTotal.setText("0");
                dsSanPhamAdapter.setRemoveSelected();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        // click nút Thanh Toán
        if (view == btnPay) {

            print = true;
            //lấy danh sách sản phẩm đang chọn
            List<SanPham> sanPhams = dsSanPhamAdapter.getListSanPham();
            sanPhamSelecteds = new ArrayList<>();
            for (int i = 0; i < sanPhams.size(); i++) {
                if (sanPhams.get(i).isSelect()) {
                    sanPhamSelecteds.add(sanPhams.get(i));
                }
            }

            for (int i = 0; i < sanPhamSelecteds.size(); i++) {
                Log.d("name", sanPhamSelecteds.get(i).getFoodNameInBill() + " : " + sanPhamSelecteds.get(i).getNum() + " : " + sanPhamSelecteds.get(i).getNoteString());
            }

            if (sanPhamSelecteds.size() > 0) {

                //goi ham send data len sv
                DownloadData downloadData = new DownloadData();
                downloadData.exeDownload("", sanPhamSelecteds, this);

            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Không có sản phẩm nào được bán")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }

        }
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (connService != null) {
            unbindService(connService);
        }
    }

    private boolean connected = false;
    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có đồng ý thoát ứng dụng")
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }).show();

    }

    // connect tới sv In
    private void Binding() {
        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        startService(intent);//启动打印服务(Bắt đầu dịch vụ in)
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    ICallback callback = new ICallback.Stub() {

        @Override
        public void onRunResult(boolean success) throws RemoteException {
        }

        @Override
        public void onReturnString(final String value) throws RemoteException {
        }

        @Override
        public void onRaiseException(int code, final String msg)
                throws RemoteException {
        }
    };

    @Override
    public void checkProduct() {

    }

    @Override
    public void setReturnItemBill(ReturnItemBill returnItem) {
        if (print == true) {
            final ReturnItemBill returnItemBill = returnItem;
            if (returnItemBill == null) {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Không thể kết nối đến server")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {

                if (returnItemBill.isSuccess()) {

                    final String nameStaff = returnItemBill.getEmployeeName();
                    String dateTime = returnItemBill.getBookingTime();

                    List<ProductReturnBill> productReturnBills = returnItemBill.getProductReturnBills();
                    List<ProductOrder> products = new ArrayList<>();
                    for (int i = 0; i < productReturnBills.size(); i++) {
                        products.add(new ProductOrder(productReturnBills.get(i).getProductName(), productReturnBills.get(i).getTypeId(), productReturnBills.get(i).getQuantity(), productReturnBills.get(i).getProductPrice(), productReturnBills.get(i).getTotalMoney()));
                    }

                    double totalMoney = returnItemBill.getTotalMoney();
                    String numOrder = returnItemBill.getOrderNumber();

                    TextView tvNameStaff = findViewById(R.id.tvNameStaff);
                    TextView tvDateTime = findViewById(R.id.tvDateTime);
                    TextView tvTotalMoney = findViewById(R.id.tvTotalMoney);

                    //                TextView tvTotalMoney = findViewById(R.id.tvTotalMoney);
                    TextView tvtvNumOrder = findViewById(R.id.tvNumOrder);

                    tvNameStaff.setText("T.N: " + nameStaff);
                    tvDateTime.setText(dateTime);
                    tvTotalMoney.setText(Untils.convertToVnd(totalMoney));
                    //                tvTotalMoney.setText(Untils.convertToVnd(totalMoney) + "");
                    tvtvNumOrder.setText("STT: " + numOrder);

                    RecyclerView rcvBill = findViewById(R.id.recyclerView_Order);
                    final ProductOrderAdapter adapter = new ProductOrderAdapter(this, products);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                            super.onLayoutChildren(recycler, state);
                            //TODO if the items are filtered, considered hiding the fast scroller here
//                           if(print) {
                            final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                            if (firstVisibleItemPosition != 0) {
                                // this avoids trying to handle un-needed calls
                                if (firstVisibleItemPosition == -1)
                                    //not initialized, or no items shown, so hide fast-scroller

                                    return;
                            }
                            final int lastVisibleItemPosition = findLastVisibleItemPosition();
                            int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                            //if all items are shown, hide the fast-scroller
                            if (adapter.getItemCount() >= itemsShown) {
                                //create bitmap order
                                final LinearLayout ln = findViewById(R.id.ln);
                                ln.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        bitmap = getBitmapFromView(ln);
                                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                        bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                                        SharedPreferences share = getSharedPreferences("PrintSetting", MODE_PRIVATE);
                                        boolean printStyle = share.getBoolean("printStyle", true);
                                        if (print == true) {
                                            if (printStyle) {
                                                // In liền
                                                try {
//                                                    woyouService.printBitmap(bitmap, null);
//                                                    woyouService.lineWrap(5, null);
                                                      woyouService.sendRAWData(out.toByteArray(), null);
                                                      woyouService.printOriginalText("\n\n\n\n", null);
                                                    dsSanPhamAdapter = new DSSanPhamAdapter(OrderActivity.this, listSanPham);
                                                    recyclerView.setAdapter(dsSanPhamAdapter);
                                                    Tong = 0;
                                                    txtTotal.setText("0");
                                                    dsSanPhamAdapter.setRemoveSelected();

                                                } catch (RemoteException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                dsSanPhamAdapter.setRemoveSelected();
                                                // Xem hóa đơn trước khi In

                                                //gui bitmap qua activity_print
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                byte[] byteArray = stream.toByteArray();
                                                Intent intent = new Intent(OrderActivity.this, PrintActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putByteArray("data", byteArray);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                            print = false;
                                        }
                                    }
                                });
                            }
//                           }
                        }
                    };
                    rcvBill.setLayoutManager(layoutManager);
                    rcvBill.setAdapter(adapter);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Không có sản phẩm nào được bán")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }

            }
        }
    }
}
