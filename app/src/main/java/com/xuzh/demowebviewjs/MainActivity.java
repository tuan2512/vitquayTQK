package com.xuzh.demowebviewjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isd.vitquaytqk.R;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class MainActivity extends Activity {

    WebView mWebView;
    LinearLayout ln;
    private static Bitmap bitmap;
    private int a;
    private TextView tv;
    private ProgressDialog pd;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ln = (LinearLayout) findViewById(R.id.ln);
//        setContentView(ln);
        initViews();
        // 设置编码(Đặt mã hóa)
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        // 支持js(Hỗ trợ js)
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        // 设置背景颜色 透明(Đặt màu nền trong suốt)
        mWebView.setBackgroundColor(Color.rgb(96, 96, 96));
        mWebView.setWebViewClient(new WebViewClientDemo());//添加一个页面相应监听类(Thêm lớp người nghe tương ứng)
        // 载入包含js的html(Tải html với js)

        mWebView.loadData("", "text/html", null);
        mWebView.loadUrl("file:///android_asset/test.html");

        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        startService(intent);//启动打印服务(Bắt đầu dịch vụ in)
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    class WebViewClientDemo extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
            //Khi mở một liên kết mới, sử dụng WebView hiện tại, hệ thống sẽ không sử dụng các trình duyệt khác
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /**
             * 注册JavascriptInterface，其中"lee"的名字随便取，如果你用"lee"，那么在html中只要用  lee.方法名()
             * 即可调用MyJavascriptInterface里的同名方法，参数也要一致
             */
            /*
            Đăng nhập JavascriptInterface, nơi tên lee "" tình cờ mất, nếu bạn sử dụng "lee", sau đó chỉ cần sử dụng html lee Tên phương thức ()
			Bạn có thể gọi cùng một tên trong phương thức MyJavascriptInterface, các tham số phải giống nhau
			*/
            mWebView.addJavascriptInterface(new JsObject(), "lee");
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

    class JsObject {

        @JavascriptInterface
        public void funAndroid(final String i) throws RemoteException {

            Bitmap bitmap = getBitmapFromView(ln);
            woyouService.printBitmap(bitmap, callback);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (connService != null) {
            unbindService(connService);
        }
    }

    public void initViews() {
//        mWebView = (WebView) findViewById(R.id.wv_view);
    }

    private IWoyouService woyouService;

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

}
