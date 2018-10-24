package com.cyht.wykc.mvp.view.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.setting.NameContract;
import com.cyht.wykc.mvp.contract.videoplay.TBSContract;
import com.cyht.wykc.mvp.presenter.setting.NamePresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author： hengzwd on 2017/9/13.
 * Email：hengzwdhengzwd@qq.com
 */

public class NameActivity extends BaseActivity<NameContract.Presenter> implements NameContract.View {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_name)
    LinearLayout llname;

    @Override
    public void showLoading() {

    }


    @Override
    public NameContract.Presenter createPresenter() {
        return new NamePresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_name;
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
        tbTittle.getRightTV().setText("保存");
        tbTittle.getRightTV().setVisibility(View.VISIBLE);
        tbTittle.getTvTittle().setText("昵称");
        tbTittle.getRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (etName.getText().length() > 6) {
//                    Toast.makeText(NameActivity.this,"请输入少于6个汉字",Toast.LENGTH_LONG).show();
//                } else {
                if (etName.getText().length() > 0) {
                    mPresenter.modifyName(etName.getText().toString());
                } else {
                    Toast.makeText(NameActivity.this,"昵称不能为空",Toast.LENGTH_LONG).show();
                }
//                }
            }
        });
        etName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    boolean touchable = event.getX() > (etName.getWidth() - etName.getTotalPaddingRight())
                            && (event.getX() < ((etName.getWidth() - etName.getPaddingRight())));
                    KLog.e(TAG + "tvhistory.getTotalPaddingRight():" + etName.getTotalPaddingRight() + "tvhistory.getPaddingRight():" + etName.getPaddingRight());
                    if (touchable) {
                        etName.getText().clear();
                        KLog.e(TAG + "触发drawright");
                        return true;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(Constants.username)) {
            etName.setText(Constants.username);
            etName.setSelection(Constants.username.length());
        }
    }

    @Override
    public void onModifySuccess(String name) {
        Constants.username = name;
        KLog.e("username:" + name);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, Constants.username);
        EventBus.getDefault().postSticky(new EventData(Constants.NAMEACTIVITY, Constants.PERSONALCENTERFRAGMENT, Constants.SETTINGFRAGMENT, Constants.DO_SET_NAME));
        onBackPressedSupport();
    }

    @Override
    public void onModifyFailue(Throwable throwable) {
        Toast.makeText(NameActivity.this, "修改昵称失败", Toast.LENGTH_SHORT).show();

    }

}
