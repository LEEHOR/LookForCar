package com.cyht.wykc.mvp.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstantsUrl;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.videoplay.TBSContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.carselect.VideoListActivity;
import com.cyht.wykc.utils.BitmapUtils;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.URL;
import com.cyht.wykc.utils.WebViewHelper;
import com.cyht.wykc.widget.LoginAlertDialog;
import com.cyht.wykc.widget.ShareDialog;
import com.cyht.wykc.widget.UnConnectView;
import com.cyht.wykc.widget.x5web.X5WebView;
import com.socks.library.KLog;
import com.tencent.mm.sdk.diffdev.IDiffDevOAuth;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Author： hengzwd on 2017/12/13.
 * Email：hengzwdhengzwd@qq.com
 */

//banner中推荐的视频页
public class TweetActivity extends BaseActivity implements ShareDialog.OnShareIndexClickListener {
    public static final String WEBVIEW_URL = "webview_url";
    public static final String WEBVIEW_TITLE = "webview_title";
    //	private NormalTittleBar mBiaotilan;
    private X5WebView mWebView;
    private ImageView ivback;
    private String url;
    private String id;
    private String logo;
    private String video;
    private String content;
    private String msgId;
    private String pushType;
    private int type;
    private int videoType;
    private int from = 0;
    private UnConnectView unConnectView;
    //	private TipLoading mTipLoading;
    private LinearLayout mContainer;
    private String webviewTitle;
    private Handler mHandler = new Handler();
    private LoginAlertDialog mDialog;
    private int action = 0;//1:QQ,2:微信,3:新浪
    private String returnurl;
    private AlertDialog mConnectDialog;
    private SwipeRefreshLayout mSwipe;
    private Bitmap bitmap = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Constants.sessionid = savedInstanceState.getString("sessionid");
            Constants.HAS_LOGIN_OR_NOT = savedInstanceState.getInt("haslogin");
            logo = savedInstanceState.getString("logo");
        } else {
            Intent intent = getIntent();
            Bundle parms = intent.getExtras();
            id = parms.getString("id");
            type = parms.getInt("type");
            videoType= parms.getInt("videoType");
            msgId = parms.getString("msgId");
            pushType= parms.getString("pushType");
            if (type == 3) {
                url = CYHTConstantsUrl.TWEET_INFO_CONTENT + id;
            } else if (type == 4) {
                url = CYHTConstantsUrl.TWEET_INFO_VIDEO + id ;
            }else if (type == 5){
                url= CYHTConstantsUrl.TWEET_INFO_IMAGE + id;
            }
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("sessionid", Constants.sessionid);
        outState.putInt("haslogin", Constants.HAS_LOGIN_OR_NOT);
        outState.putString("logo", logo);
    }

    @Override
    public TBSContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.activity_tweet;
    }


    public void initView() {
        mWebView = (X5WebView) findViewById(R.id.tbs_webview);
        ivback = (ImageView) findViewById(R.id.iv_back);
        unConnectView = (UnConnectView) findViewById(R.id.loading);
        mContainer = (LinearLayout) findViewById(R.id.cyht_webview_container);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.tbs_swipe);
        mWebView.setWebChromeClient(new TweetActivity.MyWebChromeClient());
        mWebView.setWebViewClient(webViewClient);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetUtil.checkNetWork(TweetActivity.this)) {
                    if (!unConnectView.isShow()) {
                        mWebView.setVisibility(View.INVISIBLE);
                        unConnectView.show(connectClickListener);
                    }
                } else {
                    mWebView.reload();
                }
            }
        });
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressedSupport();
        }

    }

    public void initData() {

        if (!NetUtil.checkNetWork(this)) {
            mWebView.setVisibility(View.INVISIBLE);
            unConnectView.show(connectClickListener);
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            url = WebViewHelper.addExpend(url);
            mWebView.loadUrl(url);
            KLog.e(TAG, url);
        }
    }


    private boolean shouldLoad = true;
    // Web视图
    WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            KLog.e("url" + url);
            shouldLoad = true;
            if (!NetUtil.checkNetWork(TweetActivity.this)) {
                mWebView.setVisibility(View.INVISIBLE);
                unConnectView.show(connectClickListener);
                shouldLoad = false;
            }
            if (shouldLoad && checkIndex(url))
                shouldLoad = false;
            if (shouldLoad && checKLogin(url))
                shouldLoad = false;
            if (shouldLoad && checkShare(url))
                shouldLoad = false;
            if (shouldLoad && checkTel(url))
                shouldLoad = false;
            if (shouldLoad && checkQQ(url))
                shouldLoad = false;
//            if (shouldLoad && checkBack(url))
//                shouldLoad = false;
            if (shouldLoad && checkVideoPlay(url))
                shouldLoad = true;
            if (shouldLoad) {
                if(url.startsWith("http:") || url.startsWith("https:") ) {
                    url = WebViewHelper.addExpend(url);//为当前的URL添加sessionid。
                    view.loadUrl(url);
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            KLog.e("111111111111");
            if (mSwipe.isRefreshing()) {
                mSwipe.setRefreshing(false);
            }
            ivback.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            KLog.e("url:onPageFinished"+url);
            TweetActivity.this.url = url;
            mWebView.loadUrl("javascript:hidebackicon('"+"')");
            ivback.setVisibility(View.VISIBLE);
            checkPlay(url);
        }
    };


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    }


    View.OnClickListener connectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            unConnectView.close();
            mHandler.postDelayed(errorRun, 500);
        }
    };

    Runnable errorRun = new Runnable() {
        @Override
        public void run() {
//			mWebView.loadUrl(url);
            initData();
        }
    };

    private static final String LOGIN_SUFFIX = "/app/login.htm";
    private static final String EXIT_SUFFIX = "isexit=true";
    private static final String SHARE_SUFFIX = "isshare=true";
    private static final String PLAY_SUFFIX = "video=";
    private static final String TEL_SUFFIX = "tel:";
    private static final String QQ_SUFFIX = "mqqwpa:";
    private static final String TWEET_BACK = "backIndex";
    private static final String PICTURE_URL = "pictureUrl=";


    private boolean checkIndex(String url) {
        if (url.contains(CYHTConstantsUrl.INDEX_URL)) {
            if (from == Constants.VIDEOLISTACTIVITY) {
                onBackPressedSupport();
            } else {
                String[] strings = url.split("car=");
                String carid = strings[1].trim();
                Intent intent = new Intent(TweetActivity.this, VideoListActivity.class);
                intent.putExtra(Constants.CAR_ID, carid);
                startActivity(intent);
            }
            return true;
        }
        return false;
    }

    private boolean checkTel(String url) {
        if (url.contains(TEL_SUFFIX)) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            return true;
        }
        return false;
    }


    private boolean checkVideoPlay(String url) {
        if (url.contains(PICTURE_URL)) {
            Map map = WebViewHelper.getUrlPramNameAndValue(url);
            logo = (String) map.get("pictureUrl");
            video= (String) map.get("topicId");
            KLog.e("logo:"+ logo+"video:"+video);
            Glide.with(BaseApplication.mContext).load(logo).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                }
                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    KLog.e(e.getMessage().toString());
                }
            });
            return true;
        }
        return false;
    }

    private boolean checkBack(String url) {
        if (url.contains(TWEET_BACK)) {
            onBackPressedSupport();
            return true;
        }
        return false;
    }

    private boolean checkQQ(String url) {
        if (url.contains(QQ_SUFFIX)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }
        return false;
    }

    private boolean checkPlay(String url) {
        if (url.contains(PLAY_SUFFIX)) {
            showConnectDialog();
            return true;
        }
        return false;
    }


    private void showConnectDialog() {
        if (!NetUtil.isWifiConnection(TweetActivity.this)) {//!
            if (mConnectDialog == null) {
                mConnectDialog = new AlertDialog.Builder(TweetActivity.this)
                        .setTitle(getResources().getString(R.string.dialog_connect_title))
                        .setMessage(R.string.dialog_connect_content)
                        .setNegativeButton(R.string.dialog_connect_ok, null)
                        .show();
            } else if (!mConnectDialog.isShowing()) {
                mConnectDialog.show();
            }
        }
    }

    private boolean checkShare(String url) {
        if (url.contains(SHARE_SUFFIX)) {
            Map map = WebViewHelper.getUrlPramNameAndValue(url);
            content= (String) map.get("shopName");
            try {
               content=new String(URLDecoder.decode(content,"GB2312").getBytes("GB2312"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            showShare();
            return true;
        }
        return false;
    }

    private void showShare() {
        new ShareDialog.Builder(TweetActivity.this)
                .setCanCancel(true)
                .setShareIndexClickListener(this)
                .create()
                .show();
    }

    @Override
    public void onClick(int index) {
        UMImage image;
        if (bitmap != null) {
            image = new UMImage(TweetActivity.this, BitmapUtils.ImageCompress(bitmap));
        } else {
            image = new UMImage(TweetActivity.this, logo);
        }
        ShareAction shareAction = new ShareAction(TweetActivity.this)
                .withTitle(getString(videoType==1?R.string.manufacturing_Technology:videoType==2?R.string.static_analysis:videoType==3?R.string.ynamic_appreciation:videoType==4?R.string.operation_description:videoType==5?R.string.customer_comment:R.string.app_name))
                .withText(content)
                .withMedia(image)
                .withTargetUrl(CYHTConstantsUrl.SHARE_TOPIC_URL + video)//分享后的url地址
                .setCallback(umShareListener);

        KLog.e(CYHTConstantsUrl.SHARE_URL + video);
        switch (index) {
            case ShareDialog.INDEX_WX:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                break;
            case ShareDialog.INDEX_CYCLE:
                shareAction.withTitle(content);
//                ShareAction shareAction0 = new ShareAction(TweetActivity.this)
//                        .withTitle(mWebView.getTitle())
//                        .withText(mWebView.getTitle())
//                        .withMedia(image)
//                        .withTargetUrl(CYHTConstantsUrl.SHARE_URL + video)
//                        .setCallback(umShareListener);
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
//                shareAction0.share();
                break;
            case ShareDialog.INDEX_QQ:
                shareAction.setPlatform(SHARE_MEDIA.QQ);
                break;
            case ShareDialog.INDEX_SPACE:
                shareAction.setPlatform(SHARE_MEDIA.QZONE);
                break;
            case ShareDialog.INDEX_Wb:
                shareAction.setPlatform(SHARE_MEDIA.SINA);
                break;
        }
//        if (PermissionUtils.with(this).hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            shareAction.share();
//        }else {
//            PermissionUtils.with(this).requestGroupPower(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
//                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE});
//        }

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(TweetActivity.this, " 分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TweetActivity.this, " 分享失败", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(TweetActivity.this, " 分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    private boolean checKLogin(String url) {
        if (url.contains(LOGIN_SUFFIX)) {
            if (url.contains(Constants.RETURNURL)) {
                returnurl = url.substring(url.lastIndexOf(Constants.RETURNURL) + Constants.RETURNURL.length());
                try {
                    returnurl = java.net.URLDecoder.decode(returnurl, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
//            showLoginDialog();
            Intent intent = new Intent(TweetActivity.this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (!TextUtils.isEmpty(returnurl))
                KLog.e("returnurl:"+returnurl);
                mWebView.loadUrl(WebViewHelper.addExpend(returnurl));
        }
        EventBus.getDefault().postSticky(new EventData(Constants.TWEETACTIVITY, Constants.PERSONALCENTERFRAGMENT, Constants.DO_LOGIN));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.freeMemory();
            mContainer.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {//控制网页回退
//		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack() && !url.contains(EXIT_SUFFIX)) {
//			mWebView.goBack();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}


}
