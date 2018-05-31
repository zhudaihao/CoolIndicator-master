package cn.zdh.myapplication;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.just.library.AgentWebView;

import cn.zdh.progresslibrary.CoolIndicator;


/**
 * Created by we on 2017/6/9.
 */

public class MainActivity extends AppCompatActivity {
    private AgentWebView agentWebView;
    private TextView tv_title;
    private CoolIndicator mCoolIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agentWebView = findViewById(R.id.agentWebView);
        tv_title = findViewById(R.id.tv_title);

        mCoolIndicator = this.findViewById(R.id.indicator);
        mCoolIndicator.setMax(100);

        initView();
    }

    private boolean isText;

    private void initView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WebSettings webSettings = agentWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        agentWebView.setWebViewClient(new WebViewClient());

        //适配手机大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);

        isText = getIntent().getBooleanExtra("isText", false);

        //监听加载
        agentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                mCoolIndicator.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mCoolIndicator.complete();
            }
        });


        if (!isText) {
            //设置标题
            WebChromeClient webChromeClient = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    tv_title.setText(title);
                }

            };
            // 设置setWebChromeClient对象
            agentWebView.setWebChromeClient(webChromeClient);
        }

        //设置数据
        setDate();


    }

    //设置数据
    private void setDate() {
        String videoUrl = "https://m.vip.com/?source=www&jump_https=1";
        agentWebView.loadUrl(videoUrl);
    }


    @Override
    protected void onResume() {
        agentWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        agentWebView.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        agentWebView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        agentWebView.destroy();
        super.onBackPressed();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWebView != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && agentWebView.canGoBack()) {
                agentWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
