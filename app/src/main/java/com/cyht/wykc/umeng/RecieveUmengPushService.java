package com.cyht.wykc.umeng;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.view.ExtensionActivity;
import com.cyht.wykc.mvp.view.TweetActivity;
import com.cyht.wykc.mvp.view.TweetActivity2;
import com.cyht.wykc.mvp.view.adapter.ExtensionAdapter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.setting.SettingActivity;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.SystemUtils;
import com.socks.library.KLog;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author： hengzwd on 2017/11/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class RecieveUmengPushService extends Service {

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        initUmengRecieve();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
    }

    private void initUmengRecieve() {
        handler = new Handler();
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                KLog.e("dealWithCustomMessage:" + msg.getRaw());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
                KLog.e("handleMessage");
            }

            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                KLog.e("dealWithNotificationMessage" + uMessage.extra.get("pushType"));
                super.dealWithNotificationMessage(context, uMessage);

            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                KLog.e("UMessage" + msg.activity + msg.getRaw());
                EventBus.getDefault().postSticky(new EventData(Constants.BASEAPPLICATION, Constants.PERSONALCENTERFRAGMENT));
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };

        UmengNotificationClickHandler umengNotificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                KLog.e("handlemessage");
                super.handleMessage(context, uMessage);
            }

            @Override
            public void launchApp(Context context, UMessage uMessage) {
//                KLog.e("appstatus：" + SystemUtils.getAppSatus(context, "com.cyht.wykc"));
                String pushType = uMessage.extra.get("pushType");
                int appStatus = SystemUtils.getAppSatus(context, "com.cyht.wykc");
                if (appStatus == 1) {//app处于前台使用状态
                    if (Constants.sessionid != null || PreferenceUtils.contains(BaseApplication.mContext, Constants.SESSION_ID)) {//登录过
                        if (pushType.equals("0")) {
                            Intent intent = new Intent(context, SettingActivity.class);
                            intent.putExtra("to_fragment", Constants.LETTERSFRAGMENT);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            KLog.e("launchApp：" + "pushType:" + pushType);
                        } else if (pushType.equals("1")) {
                            //其他推送，用来做广告
                            KLog.e("launchApp：" + "pushType:" + pushType);
                            Intent intent = new Intent(context, ExtensionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("msgId", uMessage.extra.get("msgId"));
                            startActivity(intent);
                        } else if (pushType.equals("2") || pushType.equals("3")) {
                            Intent intent = new Intent(context, TweetActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("msgId", uMessage.extra.get("msgId"));
                            intent.putExtra("pushType",pushType);
                            intent.putExtra("videoTitle",uMessage.extra.get("videoTitle"));
                            intent.putExtra("videoCover",uMessage.extra.get("videoCover"));
                            startActivity(intent);
                        }
                    }
                } else if (appStatus == 2) {//app处于后台运行状态
                    if (Constants.sessionid != null || PreferenceUtils.contains(BaseApplication.mContext, Constants.SESSION_ID)) {//登录过
                        if (pushType.equals("0")) {
                            Intent intent = new Intent(context, SettingActivity.class);
                            intent.putExtra("to_fragment", Constants.LETTERSFRAGMENT);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            KLog.e("launchApp：" + "pushType:" + pushType);
                        } else if (pushType.equals("1")) {
                            //其他推送，用来做广告
                            KLog.e("launchApp：" + "pushType:" + pushType);
                            Intent intent = new Intent(context, ExtensionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("msgId", uMessage.extra.get("msgId"));
                            startActivity(intent);
                        } else if (pushType.equals("2") || pushType.equals("3")) {
                            Intent intent = new Intent(context, TweetActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("msgId", uMessage.extra.get("msgId"));
                            intent.putExtra("pushType",pushType);
                            intent.putExtra("videoTitle",uMessage.extra.get("videoTitle"));
                            intent.putExtra("videoCover",uMessage.extra.get("videoCover"));
                            startActivity(intent);
                        }
                    }
                } else if (appStatus == 3) {//app没有任何activity处于后台运行
                    if (Constants.sessionid != null || PreferenceUtils.contains(BaseApplication.mContext, Constants.SESSION_ID)) {//登录过
                        Intent var3 = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                        var3.putExtra("msgId", uMessage.extra.get("msgId"));
                        var3.putExtra("pushType", pushType);
                        if (var3 == null) {
                        } else {
                            var3.setPackage((String) null);
                            var3.addFlags(268435456);
                            context.startActivity(var3);
                        }
                    } else {
                        super.launchApp(context, uMessage);
                    }
                }
            }
        };
        PushAgent.getInstance(BaseApplication.mContext).

                setMessageHandler(messageHandler);
        PushAgent.getInstance(BaseApplication.mContext).

                setNotificationClickHandler(umengNotificationClickHandler);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
