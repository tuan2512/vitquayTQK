package com.isd.vitquaytqk.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.isd.vitquaytqk.R;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class PrintActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPrint, btnPaperFeed;
    private ImageView imOrder;
    private IWoyouService woyouService;
    int myorientation;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        imOrder = (ImageView) findViewById(R.id.imOrder);
        btnPrint = (Button) findViewById(R.id.btn_print);
        btnPaperFeed = (Button) findViewById(R.id.btn_paperFeed);

        Binding();

        //get data from orderactivity
        byte[] bytes = getIntent().getExtras().getByteArray("data");
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imOrder.setImageDrawable(new BitmapDrawable(bitmap));

        btnPrint.setOnClickListener(this);
        btnPaperFeed.setOnClickListener(this);
    }

    private void autoLoadData() {
        Bundle bundle = getIntent().getExtras();
        bundle.getSerializable("bill");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_print:
                try {
                    woyouService.printBitmap(bitmap, null);
                    woyouService.lineWrap(3, null);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_paperFeed:
                try {
                    woyouService.lineWrap(3, callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
