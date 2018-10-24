package com.cyht.wykc.mvp.view.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.base.BaseContract;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.fl_container_setting)
    DrawerLayout flContainerSetting;

    @Override
    public BaseContract.presenter createPresenter() {
        return null;
    }

    @Override
    public int binLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt("to_fragment")) {
            case Constants.COLLECTIONFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new CollectionFragment());
                break;
            case Constants.HISTROYFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new HistoryFragment());
                break;
            case Constants.OPINIONFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new OpinionFragment());
                break;
            case Constants.SETTINGFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new SettingFragment());

                break;
            case Constants.LETTERSFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new LettersFragment());
                break;
            case Constants.CONTRACTFRAGMENT:
                loadRootFragment(R.id.fl_container_setting, new ContractFragment());
                break;
        }
    }

    @Override
    public void initData() {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.e("ondestroy");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            KLog.e("ondestroy");
        }
    }


    //    public static final int SELECT_CAMER = 0;
//    public static final int SELECT_PICTURE = 1;
//    public static final int SELECT_COMPELET = 2;
//    public static final String IMAGE_PATH = "path";
//    private Uri photoUri;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        KLog.e("onActivityResult");
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case SELECT_CAMER:
//            case SELECT_PICTURE:
//                if (requestCode == SELECT_PICTURE && data != null) {
//                    photoUri = data.getData();
//                }
//                Intent intent = new Intent(this, ClipImageActivity.class);
////				intent.putExtra(IMAGE_PATH, picPath);
//                intent.setData(photoUri);
//                startActivityForResult(intent, SELECT_COMPELET);
//                break;
//            case SELECT_COMPELET:
//                if (data != null) {
//                    String imgUrl = data.getStringExtra(IMAGE_PATH);
//                    if (!TextUtils.isEmpty(imgUrl)) {
//                        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, imgUrl);
//                        Glide.with(BaseApplication.mContext).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.RESULT).into(findFragment(SettingFragment.class).ivHeadPic);
//                        EventBus.getDefault().postSticky(new EventData(Constants.SETTINGACTIVITY,Constants.PERSONALCENTERFRAGMENT,Constants.DO_LOAD_HEADPIC));
//                    }
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


}
