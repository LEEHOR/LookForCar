package com.cyht.wykc.mvp.view.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstants;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.OnTextInputListener;
import com.cyht.wykc.mvp.contract.setting.OpinionContract;
import com.cyht.wykc.mvp.modles.bean.OpinionBean;
import com.cyht.wykc.mvp.presenter.setting.OpinionPresenter;
import com.cyht.wykc.mvp.view.MainActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.AnFQNumEditText;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnTouch;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class OpinionFragment extends BaseFragment<OpinionContract.Presenter> implements OpinionContract.View{


    @BindView(R.id.ll_Opinion)
    LinearLayout llopinion;
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.yijianfankui_content)
    AnFQNumEditText Content;
    @BindView(R.id.yijianfankui_submit)
    Button Submit;


    private Map<String, String> params = new HashMap<>();
    private String contenttext;

    public OpinionFragment() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
    }


    @Override
    public void showLoading() {
    }


    @Override
    public OpinionContract.Presenter createPresenter() {
        return new OpinionPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_opinion;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("意见反馈");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity) _mActivity).onBackPressedSupport();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.HAS_LOGIN_OR_NOT == 0) {
                    Intent intent = new Intent(_mActivity, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(BaseApplication.mContext, "未登录，请登录后再反馈意见", Toast.LENGTH_SHORT).show();
                } else {
                    contenttext = Content.getText();
                    if (!TextUtils.isEmpty(contenttext)) {
                        if (NetUtil.checkNetWork(BaseApplication.mContext)) {

                            mPresenter.requestOpinion(contenttext);
                        } else {
                            NetUtil.showNetDialog(BaseApplication.mContext);
                        }
                    }
                }
            }
        });
        Content.setEtHint(getResources().getString(R.string.yijianfankui_hint_content))//设置提示文字
                .setLength(100)//设置总字数
                //TextView显示类型(SINGULAR单数类型)(PERCENTAGE百分比类型)
                .setType(AnFQNumEditText.PERCENTAGE)
                .show();
        Content.setOnTextInputListener(new OnTextInputListener() {
            @Override
            public void onInput(String s) {
                if (!("").equals(s) && s != null) {
                    Submit.setClickable(true);
                    Submit.setBackgroundColor(getResources().getColor(R.color.cyht_title_text_color));
                } else {
                    Submit.setClickable(false);
                    Submit.setBackgroundColor(getResources().getColor(R.color.material_grey_500));
                }

            }
        });

    }


    @Override
    public void initData() {
    }

    @Override
    public void onOpinionSuccess(OpinionBean opinionBean) {
        Toast.makeText(_mActivity, getResources().getString(R.string.toast_yijianfankui_success), Toast.LENGTH_SHORT).show();
        ((SettingActivity) _mActivity).onBackPressedSupport();
    }

    @Override
    public void onOpinionFailure(Throwable throwable) {
        Toast.makeText(_mActivity, getResources().getString(R.string.toast_yijianfankui_failure), Toast.LENGTH_SHORT).show();

    }

}
