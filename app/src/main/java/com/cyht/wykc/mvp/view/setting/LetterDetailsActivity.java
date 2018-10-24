package com.cyht.wykc.mvp.view.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.platform.comapi.map.K;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.setting.LetterDetailsContract;
import com.cyht.wykc.mvp.presenter.setting.LettersDetailsPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Author： hengzwd on 2017/9/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class LetterDetailsActivity extends BaseActivity<LetterDetailsContract.Presenter> implements LetterDetailsContract.View {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.tv_letters_tittle)
    TextView tvLettersTittle;
//    @BindView(R.id.tv_letters_time)
//    TextView tvLettersTime;
    @BindView(R.id.tv_letters_text)
    TextView tvLettersText;

    @Override
    public void showLoading() {

    }

    @Override
    public void updateFailure(Throwable throwable) {

    }

    @Override
    public void updateSuccess(String msgId) {

    }

    @Override
    public LetterDetailsContract.Presenter createPresenter() {
        return new LettersDetailsPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_lettersdetail;
    }

    @Override
    public void initView() {
        tbTittle.getTvTittle().setText("消息详情");
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        String tittle = intent.getStringExtra("tittle");
        String content = intent.getStringExtra("content");
        if (type == 1) {
            tvLettersTittle.setText(tittle + "回复了你：");
        } else if (type == 0) {
            tvLettersTittle.setText("系统提醒:");
        }else {}
        tvLettersText.setText(content);
//        tvLettersTime.setText(time);
    }

}
