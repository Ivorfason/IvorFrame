package com.ivor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * Description: WebView
 * * @author  Ivor
 */

public class IvorWebView extends Activity {

    private WebView mShowWV;
    private ImageView mBackIV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_webview);

        mBackIV = (ImageView) findViewById(R.id.ivor_webback_iv);
        mBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IvorWebView.this.finish();
            }
        });
        mShowWV = (WebView) findViewById(R.id.ivor_searchweb_wv);
        //设置WebView属性，能够执行Javascript脚本
        mShowWV.getSettings().setJavaScriptEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        String myurl = bundle.getString("url");

        //加载需要显示的网页
        mShowWV.loadUrl(myurl);
        //设置Web视图
        mShowWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mShowWV.canGoBack()) {
            mShowWV.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

}
