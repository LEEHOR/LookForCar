package com.cyht.wykc.mvp.view.distributor;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * Author： hengzwd on 2017/9/4.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorVideoActivity extends BaseActivity {
    @BindView(R.id.wbv_video)
    WebView wbvVideo;
    @BindView(R.id.iv_back)
    ImageView ivback;

    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.activity_distributorvideo;
    }

    @Override
    public void initView() {
        WebSettings setting = wbvVideo.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setAllowFileAccess(true);
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setDatabaseEnabled(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setDefaultTextEncodingName("UTF-8");
        wbvVideo.setWebChromeClient(new WebChromeClient());
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }
    @Override
    public void initData() {

        Intent intent = getIntent();
        String url=intent.getStringExtra("url");
        KLog.e("url:"+url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wbvVideo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wbvVideo.loadUrl(url);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onPause() {
        wbvVideo.reload ();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
