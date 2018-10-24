package com.cyht.wykc.mvp.view.base;

import android.app.Notification;
import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.exception.AppExceptionHandler;
import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.bean.BrandBean;
import com.cyht.wykc.mvp.modles.bean.BrandDBbean;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.CarDBbean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.utils.AppUtils;
import com.cyht.wykc.utils.DatabaseUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.facebook.stetho.Stetho;
import com.socks.library.KLog;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author： hengzwd on 2017/8/2.
 * Email：hengzwdhengzwd@qq.com
 */

public class BaseApplication extends LitePalApplication {


    public static Context mContext;
    public static final String UPDATE_STATUS_ACTION = "com.cyht.wykc.action.UPDATE_STATUS";

    private String TAG = BaseApplication.class.getSimpleName().toString();

    {
        PlatformConfig.setWeixin("wxc4775de231f61ce7", "cd6ab62ca0e3dc3744e33c9efebcee84");
        PlatformConfig.setSinaWeibo("2232505805", "ad11ee413cf513baf1a9b549605036c5");
        PlatformConfig.setQQZone("1105786581", "rnuWjmnFJpD7CKwf");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//         程序异常交由AppExceptionHandler来处理
//        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        Stetho.initialize(//facebook  Android开发调试框架
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        UMShareAPI.get(this);
        initPush();
        startTBS();
        initDatabase();
        UpdataUUid();
        UMShareAPI.get(this);
        com.umeng.socialize.Config.dialogSwitch = false;//去掉等待的dialog
        com.umeng.socialize.Config.REDIRECT_URL = "http://www.goldidea.cc";
        SharedPreferencesUtils.put(mContext, "versioncode", AppUtils.getVersionCode(mContext));
    }

    private void UpdataUUid() {
        if (!SharedPreferencesUtils.contains(mContext, "uuid")) {
            Map<String, String> map = new HashMap<>();
            final String uuid = UUID.randomUUID().toString();
            map.put("uuid", uuid);
            HttpHelper.getInstance().initService().updateUUID(map).enqueue(new Callback<ResultBean>() {
                @Override
                public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                    if (response.isSuccessful()) {
                        ResultBean resultBean = response.body();
                        if (resultBean.getResult() == 1) {
                            SharedPreferencesUtils.put(mContext, "uuid", uuid);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultBean> call, Throwable t) {
                }
            });

        }
    }

    private void initDatabase() {
        Map<String, String> map = new HashMap<>();
        map.put("version", getCarVersion());
        HttpHelper.getInstance().initService().getCarDB(map).enqueue(new Callback<CarDBbean>() {
            @Override
            public void onResponse(Call<CarDBbean> call, Response<CarDBbean> response) {
                if (response.isSuccessful()) {
                    CarDBbean carDBbean = response.body();
                    if (carDBbean.getResult() == 1) {
                        List<CarBean> carmodels = new ArrayList<CarBean>();
                        for (CarDBbean.DataEntity.CarListEntity carEntity : carDBbean.getData().getCarList()) {
                            carmodels.add(new CarBean(carEntity.getId(), carEntity.getName(), carEntity.getBrand(), carEntity.getType() + ""));
                        }
                        DatabaseUtils.getInstance().save(CarBean.class, carmodels);
                        SharedPreferencesUtils.put(mContext, "carversion", carDBbean.getVersion());
                        KLog.e(TAG + "数据条数：" + carmodels.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<CarDBbean> call, Throwable t) {

            }
        });
        Map<String, String> brandmap = new HashMap<>();
        brandmap.put("version", getBrandVersion());
        HttpHelper.getInstance().initService().getBrandDB(brandmap).enqueue(new Callback<BrandDBbean>() {
            @Override
            public void onResponse(Call<BrandDBbean> call, Response<BrandDBbean> response) {
                if (response.isSuccessful()) {
                    BrandDBbean brandDBbean = response.body();
                    if (brandDBbean.getResult() == 1) {
                        List<BrandBean> beanList = new ArrayList<BrandBean>();
                        for (BrandDBbean.DataEntity.BrandListEntity brandEntity : brandDBbean.getData().getBrandList()) {
                            beanList.add(new BrandBean(brandEntity.getId(), brandEntity.getName(), brandEntity.getCode(), brandEntity.getLogo(), brandEntity.getType() + ""));
                        }
                        DatabaseUtils.getInstance().save(BrandBean.class, beanList);
                        SharedPreferencesUtils.put(mContext, "brandversion", brandDBbean.getData().getVersion());
                        KLog.e(TAG + "数据条数：" + beanList.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<BrandDBbean> call, Throwable t) {
            }
        });

    }

    //获取拥有的车型数据库版本
    private String getCarVersion() {
        if (PreferenceUtils.contains(mContext, "carversion")) {
            return PreferenceUtils.getPrefString(mContext, "carversion", "");
        }
        return "0";
    }


    //获取拥有的车型数据库版本
    private String getBrandVersion() {
        if (PreferenceUtils.contains(mContext, "brandversion")) {
            return PreferenceUtils.getPrefString(mContext, "brandversion", "");
        }
        return "0";
    }

    private void startTBS() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                KLog.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                KLog.e("app", " onCoreInitFinished is ");

                // TODO Auto-generated method stub
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                KLog.d("app", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                KLog.d("app", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                KLog.d("app", "onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(mContext, cb);//x5  内核预先加载   不写会发生各种 网页问题
    }

    private void initPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                KLog.e("dealWithCustomAction");
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                KLog.e(TAG, "device token: " + deviceToken);
                Constants.devicestoken = deviceToken;
                SharedPreferencesUtils.put(mContext, Constants.DEVICESTOKEN, Constants.devicestoken);
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
                saveToken(Constants.devicestoken!=null?Constants.devicestoken:(String) SharedPreferencesUtils.get(mContext,Constants.DEVICESTOKEN,""));
            }

            @Override
            public void onFailure(String s, String s1) {
                KLog.e(TAG, "register failed: " + s + " " + s1);
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        PushAgent.getInstance(mContext).onAppStart();
        mPushAgent.setDebugMode(true);//友盟调试日志输出，正式发布的时候，改为false

        //此处是完全自定义处理设置，两个例子，任选一种即可
//        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//        mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);
    }


    private void saveToken(final String token) {
        Map<String, String> map = new HashMap<>();
        map.put("sessionid", PreferenceUtils.getPrefString(mContext,Constants.SESSION_ID,""));
        map.put("token",token);
        map.put("system",Constants.ANDROID);
        HttpHelper.getInstance().initService().saveToken(map).enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                if (response.isSuccessful()) {
                    ResultBean resultBean=response.body();
                    if (resultBean.getResult()==1) {
                        KLog.e("token上传成功："+token);
                    }else {
                        KLog.e("token上传失败："+token);
                    }
                }else {
                    KLog.e("token上传失败："+token);
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                KLog.e("token上传失败："+token);
                KLog.e("throwable："+t.getMessage());
            }
        });
    }
}
