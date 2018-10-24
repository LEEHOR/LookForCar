package com.cyht.wykc.utils;



import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.socks.library.KLog;

import java.util.List;

/**
 * Author： hengzwd on 2017/11/9.
 * Email：hengzwdhengzwd@qq.com
 */

public class SystemUtils {
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        KLog.e(android.os.Process.myPid());
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                KLog.e("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName+processInfos.get(i).pid));
                return true;
            }
        }
        KLog.e("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }


    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static  int getAppSatus(Context context, String pageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }
//    public static void startDetailActivity(Context context, String name, String price,
//                                           String detail){
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("name", name);
//        intent.putExtra("price", price);
//        intent.putExtra("detail", detail);
//        context.startActivity(intent);
//    }
}
