package com.cyht.wykc.mvp.view.setting;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.TokenWatcher;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstants;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.setting.SettingContract;
import com.cyht.wykc.mvp.presenter.setting.SettingPresenter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.mvp.view.distributor.DistributorActivity;
import com.cyht.wykc.utils.FileUtils;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.BottomMenuDialog;
import com.cyht.wykc.widget.CircleImageView;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.util.Const;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class SettingFragment extends BaseFragment<SettingContract.Presenter> implements SettingContract.View, View.OnClickListener {


    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.iv_head_pic)
    CircleImageView ivHeadPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R.id.rl_headpic)
    RelativeLayout rlHeadpic;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_clearcache)
    RelativeLayout rlClearcache;

    private AlertDialog dialog;
    private Uri photoUri;

    public static final int SELECT_CAMER = 0;
    public static final int SELECT_PICTURE = 1;
    public static final int SELECT_COMPELET = 2;
    public static final String IMAGE_PATH = "path";

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
        if (eventData.to == Constants.SETTINGFRAGMENT || eventData.to1 == Constants.SETTINGFRAGMENT) {
            if (eventData.from == Constants.NAMEACTIVITY && eventData.doWhat == Constants.DO_SET_NAME) {
                tvName.setText(Constants.username);
            }
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void unbindFailure(Throwable t) {

    }

    @Override
    public void unbindSuccess() {

    }

    @Override
    public SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_settting;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        rlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(_mActivity,AboutActivity.class);
                _mActivity.startActivity(intent);
            }
        });
        tbTittle.getTvTittle().setText("设置");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity) _mActivity).onBackPressedSupport();
            }
        });
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.USERNAME)) {
            tvLoginout.setVisibility(View.VISIBLE);
        }
        iniDialog();
        tvLoginout.setOnClickListener(this);
        rlHeadpic.setOnClickListener(this);
        rlClearcache.setOnClickListener(this);
        rlName.setOnClickListener(this);
    }

    private void iniDialog() {
        dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("确定要退出登录吗")
                .setNegativeButton(R.string.cyht_button_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenceUtils.remove(BaseApplication.mContext, Constants.USERNAME);
                        PreferenceUtils.remove(BaseApplication.mContext, Constants.SESSION_ID);
                        PreferenceUtils.remove(BaseApplication.mContext, Constants.TOUXIANG);
                        Constants.username="";
                        Constants.touxiang="";
                        Constants.sessionid="";
                        tvLoginout.setVisibility(View.GONE);
                        EventBus.getDefault().postSticky(new EventData(Constants.SETTINGFRAGMENT, Constants.PERSONALCENTERFRAGMENT, Constants.DO_UNLOGIN));
                        _mActivity.onBackPressed();
                    }
                })
                .setNeutralButton(R.string.cyht_button_cancel, null).create();
    }

    @Override
    public void initData() {
        loadHeadPic(Constants.touxiang);
        tvName.setText(Constants.username);
        setCache();
    }


    private void  loadHeadPic(String touxiang)
    {
        Imageloader.loadImage(touxiang,ivHeadPic,R.mipmap.pcenter_user);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loginout:
                dialog.show();
                break;
            case R.id.rl_headpic:
                showPick();
                break;
            case R.id.rl_clearcache:
                if (FileUtils.deleteDir(new File(CYHTConstants.SAVE_DIR_BASE))) {
                    Toast.makeText(BaseApplication.mContext, getResources().getString(R.string.appset_toast_clear_cache_success), Toast.LENGTH_SHORT).show();
                    setCache();
                }
                break;
            case R.id.rl_name:
                Intent intent = new Intent(_mActivity, NameActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setCache() {
        int dirSize = (int) FileUtils.getFileDirSize(new File(
                CYHTConstants.SAVE_DIR_BASE));
        String fileSize = FileUtils.getFilesize(dirSize + "");
        tvCache.setText("当前缓存 " + fileSize);
    }

    private PermissionUtils.PermissionGrant permissionGrant=new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch(requestCode){
                case PermissionUtils.CODE_CAMERA:
                    takePhoto();
                    break;

            }
        }
    };
    private void showPick() {
        new BottomMenuDialog.Builder(_mActivity)
                .setCanCancel(true)
                .addMenu("拍照", new BottomMenuDialog.OnBottomMenuClickListener() {
                    @Override
                    public void onClick() {
                        if (PermissionUtils.isPermissionsGranted(getActivity(),new int[]{PermissionUtils.CODE_CAMERA})) {
                            takePhoto();
                        }else {
                            PermissionUtils.requestPermission(SettingFragment.this,PermissionUtils.CODE_CAMERA,permissionGrant);
                        }

                    }
                })
                .addMenu("从手机选择", new BottomMenuDialog.OnBottomMenuClickListener() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "请选择图片"), SELECT_PICTURE);
                    }
                })
                .create()
                .show();
    }


    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            /**
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = _mActivity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_CAMER);
        } else {
            Toast.makeText(_mActivity, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.e("onActivityResult");
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SELECT_CAMER:
            case SELECT_PICTURE:
                if (requestCode == SELECT_PICTURE && data != null) {
                    photoUri = data.getData();
                }
                Intent intent = new Intent(_mActivity, ClipImageActivity.class);
//				intent.putExtra(IMAGE_PATH, picPath);
                intent.setData(photoUri);
                startActivityForResult(intent, SELECT_COMPELET);
                break;
            case SELECT_COMPELET:
                if (data != null) {
                    String imgUrl = data.getStringExtra(IMAGE_PATH);
                    if (!TextUtils.isEmpty(imgUrl)) {
                        Constants.touxiang=imgUrl;
                        KLog.e("touxiang:"+ Constants.touxiang);
                        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, imgUrl);
                        loadHeadPic(Constants.touxiang);
                        EventBus.getDefault().postSticky(new EventData(Constants.SETTINGFRAGMENT, Constants.PERSONALCENTERFRAGMENT, Constants.DO_LOAD_HEADPIC));
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults, permissionGrant);
    }
}
