package com.cyht.wykc.mvp.view.setting;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstants;
import com.cyht.wykc.mvp.contract.setting.ClipContract;
import com.cyht.wykc.mvp.presenter.setting.ClipPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.BitmapUtils;
import com.cyht.wykc.utils.ImageUtil;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.ClipImage.ClipViewLayout;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cyht.wykc.mvp.view.setting.SettingFragment.IMAGE_PATH;


/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends BaseActivity<ClipContract.Presenter> implements ClipContract.View, View.OnClickListener {


    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.clip_layout)
    ClipViewLayout clipLayout;
    @BindView(R.id.clip_btn_cancel)
    TextView clipBtnCancel;
    @BindView(R.id.clip_btn_ok)
    TextView clipBtnOk;
    private Bitmap bitmap;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clip_btn_cancel:
                finish();
                break;
            case R.id.clip_btn_ok:
                generateUriAndUpload();
                break;
        }
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndUpload() {
        //调用返回剪切图
        bitmap = clipLayout.clip();
        if (bitmap == null) {
            Toast.makeText(ClipImageActivity.this, "图片剪切失败", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(CYHTConstants.SAVE_DIR_ICON);
        if (!file.exists())
            file.mkdirs();

        int size = bitmap.getByteCount()/1024;;
        bitmap = BitmapUtils.ImageCompress(bitmap);
        String imgName = "header" + System.currentTimeMillis() + ".jpg";
        ImageUtil.saveBitmapToSDCard(bitmap, CYHTConstants.SAVE_DIR_ICON, imgName);
        File file1 = new File(CYHTConstants.SAVE_DIR_ICON.concat(imgName));
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("touxiang", file1.getName(), requestFile);


        mPresenter.updateHeadPic(body);
    }

    @Override
    public ClipContract.Presenter createPresenter() {
        return new ClipPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_clip_image;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());

        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        tbTittle.getTvTittle().setText("上传头像");
        clipBtnCancel.setOnClickListener(this);
        clipBtnOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置图片资源
        clipLayout.setImageSrc(getIntent().getData());
    }

    @Override
    public void initData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(@Nullable Throwable e) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onUpdateSuccess(String  picUrl) {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        data.putExtra(IMAGE_PATH,picUrl);
        ClipImageActivity.this.finish();
    }

    @Override
    public void onUpdateFailue(Throwable throwable) {
        Toast.makeText(ClipImageActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }
    }
}
