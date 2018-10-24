package com.cyht.wykc.mvp.view.setting;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstantsUrl;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.setting.PersonalCenterContract;
import com.cyht.wykc.mvp.presenter.setting.PersonalCenterPresenter;
import com.cyht.wykc.mvp.view.MainActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.widget.CircleImageView;
import com.cyht.wykc.widget.ERCodeAlertDialog;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.util.Const;

import butterknife.BindView;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class PersonalCenterFragment extends BaseFragment<PersonalCenterContract.Presenter> implements PersonalCenterContract.View, View.OnClickListener {

    @BindView(R.id.iv_pic_head)
    CircleImageView ivPicHead;
    @BindView(R.id.tv_pic_head)
    TextView tvPicHead;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.ll_collection)
    LinearLayout llCollection;
    @BindView(R.id.rl_letters)
    RelativeLayout rlLetters;
    @BindView(R.id.ll_downloadpath)
    LinearLayout llDownloadpath;
    @BindView(R.id.ll_contract)
    LinearLayout llContract;
    @BindView(R.id.ll_Opinion)
    LinearLayout llOpinion;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.tv_letters_count)
    TextView tvlettercount;
    private ERCodeAlertDialog mCodeDialog;

    public static PersonalCenterFragment newInstance() {
        return new PersonalCenterFragment();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
        if (eventData.to == Constants.PERSONALCENTERFRAGMENT || eventData.to1 == Constants.PERSONALCENTERFRAGMENT) {
            if (eventData.from == Constants.SETTINGFRAGMENT && eventData.doWhat == Constants.DO_UNLOGIN) {
                ivPicHead.setImageDrawable(getResources().getDrawable(R.mipmap.pcenter_user));
                tvPicHead.setText("");
                Constants.HAS_LOGIN_OR_NOT = 0;
                ((MainActivity) _mActivity).showFragment(0);
            }

            if (eventData.from == Constants.SETTINGFRAGMENT && eventData.doWhat == Constants.DO_LOAD_HEADPIC) {
                KLog.e("touxiang" + Constants.touxiang);
                loadHeadPic(Constants.touxiang);
            }

            if (eventData.from == Constants.NAMEACTIVITY && eventData.doWhat == Constants.DO_SET_NAME) {
                tvPicHead.setText(Constants.username);
            }

            if (eventData.from == Constants.TBSWEBVIEW && eventData.doWhat == Constants.DO_LOGIN) {
                setPersonInfo(Constants.username, Constants.touxiang);
            }

            if (eventData.from == Constants.TWEETACTIVITY && eventData.doWhat == Constants.DO_LOGIN) {
                setPersonInfo(Constants.username, Constants.touxiang);
            }

            if (eventData.from == Constants.LETTERSFRAGMENT && eventData.doWhat == Constants.BE_REFRESH) {
                mPresenter.requestMsgCount();
            }

            if (eventData.from == Constants.BASEAPPLICATION) {
                mPresenter.requestMsgCount();
            }

        }
    }


    @Override
    public void showLoading() {

    }


    private void loadHeadPic(String touxiang) {
        Imageloader.loadImage(touxiang,ivPicHead,R.mipmap.pcenter_user);
    }

    public void setPersonInfo(String name, String touxiang) {
        tvPicHead.setText(name);
        loadHeadPic(touxiang);
        mPresenter.requestMsgCount();
    }

    @Override
    public PersonalCenterContract.Presenter createPresenter() {
        return new PersonalCenterPresenter(this);
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_personcenter;
    }


    @Override
    public void initView() {
        llCollection.setOnClickListener(this);
        llContract.setOnClickListener(this);
        llDownloadpath.setOnClickListener(this);
        llHistory.setOnClickListener(this);
        rlLetters.setOnClickListener(this);
        llOpinion.setOnClickListener(this);
        llSetting.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        mCodeDialog = new ERCodeAlertDialog(_mActivity);
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.USERNAME)) {
            Constants.HAS_LOGIN_OR_NOT = 1;
            Constants.touxiang = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.TOUXIANG, "");
            Constants.sessionid = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.SESSION_ID, "");
            Constants.username = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.USERNAME, "");
            setPersonInfo(Constants.username, Constants.touxiang);
            mPresenter.requestMsgCount();
            KLog.e("touxiang" + Constants.touxiang);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(_mActivity, SettingActivity.class);
        switch (v.getId()) {
            case R.id.ll_history:
                intent.putExtra("to_fragment", Constants.HISTROYFRAGMENT);
                _mActivity.startActivity(intent);
                break;
            case R.id.ll_collection:
                intent.putExtra("to_fragment", Constants.COLLECTIONFRAGMENT);
                _mActivity.startActivity(intent);
                break;
            case R.id.rl_letters:
                intent.putExtra("to_fragment", Constants.LETTERSFRAGMENT);
                _mActivity.startActivity(intent);
                break;
            case R.id.ll_downloadpath:
                showERCodeDialog();
                break;
            case R.id.ll_contract:
                intent.putExtra("to_fragment", Constants.CONTRACTFRAGMENT);
                _mActivity.startActivity(intent);
                break;
            case R.id.ll_Opinion:
                intent.putExtra("to_fragment", Constants.OPINIONFRAGMENT);
                _mActivity.startActivity(intent);
                break;
            case R.id.ll_setting:
                intent.putExtra("to_fragment", Constants.SETTINGFRAGMENT);
                _mActivity.startActivity(intent);
                break;
        }
    }

    private void showERCodeDialog() {
        mCodeDialog.builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setImg(CYHTConstantsUrl.DOWNLOAD_IMAGE_URL)
                .show();
    }

    @Override
    public void onRequestFailure(Throwable throwable) {
        KLog.e("onRequestFailure");
        tvlettercount.setVisibility(View.GONE);
    }

    @Override
    public void onRequestSuccess(int msgcount) {
        KLog.e("onRequestSuccess");
        if (msgcount > 0) {
            tvlettercount.setVisibility(View.VISIBLE);
        } else {
            tvlettercount.setVisibility(View.GONE);
        }
        tvlettercount.setText(msgcount + "");
    }
}
