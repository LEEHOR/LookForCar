package com.cyht.wykc.mvp.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.LoginContract;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.mvp.presenter.LoginPresenter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.setting.PersonalCenterFragment;
import com.cyht.wykc.mvp.view.setting.SettingActivity;
import com.cyht.wykc.utils.KeyBoardUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.cyht.wykc.widget.BlockTextView;
import com.cyht.wykc.widget.LoadingDialog;
import com.cyht.wykc.widget.MobilePhoneEditText;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import anet.channel.strategy.dispatch.DispatchEvent;
import butterknife.BindView;

/**
 * Author： hengzwd on 2017/12/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.view {
    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.et_mobilephone)
    MobilePhoneEditText etMobilephone;
    @BindView(R.id.btv_verification)
    BlockTextView btvVerification;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.login_btn_wx)
    ImageView loginBtnWx;
    @BindView(R.id.login_btn_qq)
    ImageView loginBtnQq;
    @BindView(R.id.login_btn_wb)
    ImageView loginBtnWb;
    @BindView(R.id.ll_login)
    LinearLayout lllogin;

    private LoadingDialog alertDialog;

    private int action = 0;//1:QQ,2:微信,3:新浪

    @Override
    public void showLoading() {

    }
    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("登录");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressedSupport();
            }
        });
        loginBtnWx.setOnClickListener(wxClick);
        loginBtnQq.setOnClickListener(qqClick);
        loginBtnWb.setOnClickListener(wbClick);
        btvVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMobilephone.getPhoneNumber().length()==11) {
                    btvVerification.startGetCount();
                    mPresenter.getVerificationCode(etMobilephone.getPhoneNumber());
//                    Toast.makeText(LoginActivity.this,etMobilephone.getPhoneNumber()+"",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"请输入正确手机号码",Toast.LENGTH_LONG).show();
                }
            }
        });


        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==6&&etMobilephone.getPhoneNumber().length()==11) {
                    tvLogin.setClickable(true);
                    tvLogin.setBackgroundColor(Color.parseColor("#2fc1ff"));
                }else {
                    tvLogin.setClickable(false);
                    tvLogin.setBackgroundColor(Color.parseColor("#969798"));
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMobilephone.getPhoneNumber().length()!=11) {
                    Toast.makeText(LoginActivity.this,"请输入十一位手机号",Toast.LENGTH_LONG).show();
                    return;
                }
                if (etVerificationCode.getText().length()!=6) {
                    Toast.makeText(LoginActivity.this,"请输入六位验证码",Toast.LENGTH_LONG).show();
                    return;
                }
                Map map = new HashMap();
                map.put("usercode",etMobilephone.getPhoneNumber());
                map.put("xingming",etMobilephone.getPhoneNumber());
                map.put("typevalue","0");
                map.put("mobilecode",etVerificationCode.getText().toString());
                map.put(Constants.DEVICESTOKEN, Constants.devicestoken != null && Constants.devicestoken != "" ? Constants.devicestoken : (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.DEVICESTOKEN, ""));
                map.put(Constants.SYSTEM, Constants.ANDROID);
                alertDialog.show();
                mPresenter.phoneLogin(map);

            }
        });

//        alertDialog= new AlertDialog.Builder(this).setView(R.layout.dialog_loging).create();
        alertDialog = new LoadingDialog(this);
    }

    @Override
    public void initData() {
    }


    @Override
    public void onLoginFailue(Throwable throwable) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_success), Toast.LENGTH_SHORT).show();
        Constants.HAS_LOGIN_OR_NOT = 1;
        String username = loginBean.getUsername();
        String touxiang = loginBean.getTouxiang();
        String sessionid = loginBean.getSessionid();
        Constants.sessionid = sessionid;
        Constants.touxiang = touxiang;
        Constants.username = username;
        KLog.e("username:" + touxiang);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SESSION_ID, sessionid);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, username);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, touxiang);
        setResult(1);
        finish();
    }

    @Override
    public void onPhoneLoginFailure(LoginBean resultBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, resultBean.getMsg(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPhoneLoginSuccess(LoginBean loginBean) {
        alertDialog.dismiss();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_toast_success), Toast.LENGTH_SHORT).show();
        Constants.HAS_LOGIN_OR_NOT = 1;
        String username = loginBean.getUsername();
        String touxiang = loginBean.getTouxiang();
        String sessionid = loginBean.getSessionid();
        Constants.sessionid = sessionid;
        Constants.touxiang = touxiang;
        Constants.username = username;
        KLog.e("username:" + touxiang);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SESSION_ID, sessionid);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.USERNAME, username);
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.TOUXIANG, touxiang);
        setResult(1);
        finish();
    }

    @Override
    public void onVerificationResult(String result) {
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();

    }

    private void trilateralLogin() {
        UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
        switch (action) {
            case 1:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                KLog.e("share");
                break;
            case 2:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case 3:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }

    View.OnClickListener qqClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 1;
            trilateralLogin();
        }
    };

    View.OnClickListener wxClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 2;
            trilateralLogin();
        }
    };

    View.OnClickListener wbClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            action = 3;
            trilateralLogin();
        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            KLog.e("onComplete");
            if (SHARE_MEDIA.SINA.equals(platform)) {
                otherlogin(data.get("uid"), data.get("screen_name"), data.get("profile_image_url"));
            } else {
                otherlogin(data.get("openid"), data.get("screen_name"), data.get("profile_image_url"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            KLog.e("shareonError:" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            KLog.e("shareononCancel");

        }
    };

    private void otherlogin(String usercode, String xingming, String photo) {
        alertDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put(Constants.USERCODE, usercode);
//        params.put(Constants.XINGMING, xingming);
        KLog.e("xingming:"+xingming);
        params.put(Constants.PHOTO, photo);
        params.put(Constants.DEVICESTOKEN, Constants.devicestoken != null && Constants.devicestoken != "" ? Constants.devicestoken : (String) SharedPreferencesUtils.get(BaseApplication.mContext, Constants.DEVICESTOKEN, ""));
        params.put(Constants.TYPEVALUE, action + "");
        params.put(Constants.SYSTEM, Constants.ANDROID);
        mPresenter.otherLogin(params,xingming);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
