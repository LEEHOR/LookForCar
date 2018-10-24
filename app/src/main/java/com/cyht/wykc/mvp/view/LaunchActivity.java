package com.cyht.wykc.mvp.view;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstants;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.umeng.RecieveUmengPushService;
import com.cyht.wykc.utils.DisplayUtils;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.socks.library.KLog;
import com.umeng.message.inapp.InAppMessageManager;

import butterknife.ButterKnife;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class LaunchActivity extends BaseActivity {


    private long time;
    private static final long WAIT_TIME = 3 * 1000;
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            //权限得到授权
            KLog.e("onPermissionGranted",requestCode+"");
            switch (requestCode){
                case PermissionUtils.CODE_MULTI_ESSENTIAL_PERMISSION:
                    noAdJump();
                    break;
            }
        }
    };


    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.qidong;
    }

    @Override
    public void initView() {
        //启动服务
        Intent intent = new Intent(this, RecieveUmengPushService.class);
        startService(intent);

    }

    @Override
    public void initData() {
        time = System.currentTimeMillis();
        PermissionUtils.requestMultiEssentialPermissions(this,mPermissionGrant);
        Constants.sessionid = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.SESSION_ID, null);
        Constants.carid = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.CAR_ID, "");
        getScreen();
        Uri uri = getIntent().getData();
        if (uri != null) {
            String test1 = uri.getQueryParameter("arg0");
            String test2 = uri.getQueryParameter("arg1");
            KLog.e(test1 + test2);
        }
    }

    private void noAdJump() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... Void) {
                long currentTime = System.currentTimeMillis();
                long distanceTime = currentTime - time;
                if (distanceTime < WAIT_TIME) {
                    try {
                        Thread.sleep(WAIT_TIME - distanceTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                jumpWhenCanClick();
            }
        }.execute();
    }

    private void jumpWhenCanClick() {
        Intent intent;
        String username = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.USERNAME, "");
        if (!TextUtils.isEmpty(username)) Constants.HAS_LOGIN_OR_NOT = 1;//已经登录过
        intent = new Intent(LaunchActivity.this, MainActivity.class);
        if (getIntent().getExtras() != null) {
            KLog.e(getIntent().getExtras().getString("pushType"));
            intent.putExtra("pushType", getIntent().getExtras().getString("pushType"));
            intent.putExtra("msgId", getIntent().getExtras().getString("msgId"));
        }
        startActivity(intent);
        finish();
    }

    private void getScreen() {
        CYHTConstants.screenWidth = DisplayUtils.getScreenWidth(this);
        CYHTConstants.screenHeight = DisplayUtils.getScreenHeight(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        KLog.e("onRequestPermissionsResult",permissions.length+"");

        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

    }
}
