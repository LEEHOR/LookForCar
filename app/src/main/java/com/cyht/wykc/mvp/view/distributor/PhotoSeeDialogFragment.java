package com.cyht.wykc.mvp.view.distributor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.utils.KeyBoardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author： hengzwd on 2018/3/29.
 * Email：hengzwdhengzwd@qq.com
 */

public class PhotoSeeDialogFragment extends AppCompatDialogFragment {


    Unbinder unbinder;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.fl_photo)
    FrameLayout flPhoto;
    private int mAnimStyle = R.style.PhotoSeeAnimation;
    private String photoUrl;


   public static PhotoSeeDialogFragment newInstance(String url){
       PhotoSeeDialogFragment photoSeeDialogFragment= new PhotoSeeDialogFragment();
       Bundle arg= new Bundle();
       arg.putString("photoUrl",url);
       photoSeeDialogFragment.setArguments(arg);
       return photoSeeDialogFragment;
   }

    public void setAnimationStyle(@StyleRes int style) {
       this.mAnimStyle = style;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            photoUrl = args.getString("photoUrl");
        }
        View view = inflater.inflate(R.layout.fragment_photosee, container, false);
        unbinder = ButterKnife.bind(this,view);
        Imageloader.loadImage(photoUrl,ivPhoto,Imageloader.NO_DEFAULT);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        ImmersionBar.with(this, dialog)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setWindowAnimations(mAnimStyle);
        }
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_photo, R.id.fl_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
            case R.id.fl_photo:
                dismiss();
                break;
        }
    }
}
