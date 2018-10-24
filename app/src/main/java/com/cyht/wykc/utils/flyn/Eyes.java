package com.cyht.wykc.utils.flyn;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.socks.library.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Eyes {


    public static void setStatusBarColor(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.translucentStatusBar(activity);
        }
    }

    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }

    public static void setStatusBarLightMode(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
            if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
                //设置状态栏为指定颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
                    activity.getWindow().setStatusBarColor(color);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                    //调用修改状态栏颜色的方法
                    setStatusBarColor(activity, color);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
//                activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(color);

                //fitsSystemWindow 为 false, 不预留系统栏位置.
                ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    ViewCompat.requestApplyInsets(mChildView);
                }
            }else {

                translucentStatusBar(activity);//设置半透明statusbar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
                    setStatusBarBackgroundColor(activity,"#000000");
                }

            }
        }
    }


    public static void setStatusBarLightForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout,
                                                             CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusBarWhiteForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusBarColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusBarWhiteForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusBarColor);
        }
    }


    /**
     * MIUI  小米 android 6.0之前的沉浸支持透明白色字体和透明黑色字体
     */
    static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//6.0
            Class<? extends Window> clazz = activity.getWindow().getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 设置小米手机状态栏字体图标颜色模式，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {//状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {//清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
                KLog.e("exception:"+e.getMessage().toString());
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     */
    static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    private static  void setStatusBarBackgroundColor(Activity activity,final String colorPref) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (colorPref != null && !colorPref.isEmpty()) {
                final Window window = activity.getWindow();
                // Method and constants not available on all SDKs but we want to be able to compile this code with any SDK
                //SDK19:WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(0x04000000);      //SDK21:WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(0x80000000);
                try {
                    // Using reflection makes sure any 5.0+ device will work without having to compile with SDK level 21
                    window.getClass().getDeclaredMethod("setStatusBarColor", int.class).invoke(window, Color.parseColor(colorPref));
                } catch (IllegalArgumentException ignore) {
                    KLog.e( "Invalid hexString argument, use f.i. '#999999'");
                } catch (Exception ignore) {
                    // this should not happen, only in case Android removes this method in a version > 21
                    KLog.w( "Method window.setStatusBarColor not found for SDK level " + Build.VERSION.SDK_INT);
                }
            }
        }
    }
    static void setContentTopPadding(Activity activity, int padding) {
        ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.setPadding(0, padding, 0, 0);
    }

    static int getPxFromDp(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
