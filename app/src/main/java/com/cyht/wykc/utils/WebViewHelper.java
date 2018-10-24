/*
 * Copyright (c) 2016. Vv <envyfan@qq.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cyht.wykc.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cyht.wykc.common.Constants;
import com.socks.library.KLog;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：Vv on 2015/7/21 16:20
 * Mail：envyfan@qq.com
 * Description：UI帮助类
 */
public class WebViewHelper {

    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebViewSettings(Context context, WebView webView) {
        WebSettings webSetting = webView.getSettings();
        //支持js
        webSetting.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // 是否允许获取WebView的内容URL ，可以让WebView访问ContentPrivider存储的内容。 默认true
        webSetting.setAllowContentAccess(true);
        //设置可以访问文件
        webSetting.setAllowFileAccess(true);
        webSetting.setSavePassword(false);
        //设置WebView是否保存表单数据，默认true，保存数据。
        webSetting.setSaveFormData(false);
        //支持自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
        //支持缩放，默认为true。是下面那个的前提
        webSetting.setSupportZoom(true);
        //设置内置的缩放控件。若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放
        webSetting.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSetting.setDisplayZoomControls(false);
        //支持多窗口
        webSetting.setSupportMultipleWindows(false);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSetting.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSetting.setLoadWithOverviewMode(true);
        /**
         * 从Android5.0以后，当一个安全的站点（https）去加载一个非安全的站点（http）时，需要配置Webview加载内容的混合模式，一共有如下三种模式：
         MIXED_CONTENT_NEVER_ALLOW：Webview不允许一个安全的站点（https）去加载非安全的站点内容（http）,比如，https网页内容的图片是http链接。强烈建议App使用这种模式，因为这样更安全。
         MIXED_CONTENT_ALWAYS_ALLOW：在这种模式下，WebView是可以在一个安全的站点（Https）里加载非安全的站点内容（Http）,这是WebView最不安全的操作模式，尽可能地不要使用这种模式。
         MIXED_CONTENT_COMPATIBILITY_MODE：在这种模式下，当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器的风格。一些不安全的内容（Http）能被加载到一个安全的站点上（Https），
         而其他类型的内容将会被阻塞。这些内容的类型是被允许加载还是被阻塞可能会随着版本的不同而改变，并没有明确的定义。这种模式主要用于在App里面不能控制内容的渲染，但是又希望在一个安全的环境下运行。

         在Android5.0以下，默认是采用的MIXED_CONTENT_ALWAYS_ALLOW模式，即总是允许WebView同时加载Https和Http；而从Android5.0开始，默认用MIXED_CONTENT_NEVER_ALLOW模式，即总是不允许WebView同时加载Https和Http。
         * */
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 支持内容重新布局 图片过大时自动适应屏幕： 缩放排版:SINGLE_COLUMN 适应屏幕:NORMAL
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        /**
         * 定位是否可用，默认为true。请注意，为了确保定位API在WebView的页面中可用，必须遵守如下约定:
         (1) app必须有定位的权限，参见ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION；
         (2) app必须提供onGeolocationPermissionsShowPrompt(String, GeolocationPermissions.Callback)回调方法的实现，在页面通过JavaScript定位API请求定位时接收通知。
         * */
        webSetting.setGeolocationEnabled(true);

        /**
         * 基于WebView导航的类型使用缓存：正常页面加载会加载缓存并按需判断内容是否需要重新验证。
         * 如果是页面返回，页面内容不会重新加载，直接从缓存中恢复。setCacheMode允许客户端根据指定的模式来
         * 使用缓存。
         * LOAD_DEFAULT 默认加载方式
         * LOAD_CACHE_ELSE_NETWORK 按网络情况使用缓存
         * LOAD_NO_CACHE 不使用缓存
         * LOAD_CACHE_ONLY 只使用缓存
        */
        if (NetUtil.checkNetWork(context)) {
            webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        //是否允许数据库存储。默认false。查看setDatabasePath API 如何正确设置数据库存储。
        webSetting.setDatabaseEnabled(true);
        //是否存储页面DOM结构，默认false。
         webSetting.setDomStorageEnabled(true);
        // 是否允许Cache，默认false。考虑需要存储缓存，应该为缓存指定存储路径setAppCachePath
        webSetting.setAppCacheEnabled(true);
        String appCacheDir = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        //设置存储定位数据库的位置，考虑到位置权限和持久化Cache缓存，Application需要拥有指定路径的
        webSetting.setGeolocationDatabasePath(appCacheDir);
        //设置Cache API缓存路径
        webSetting.setAppCachePath(appCacheDir);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebViewSettings(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setPluginState(WebSettings.PluginState.ON);
        //支持js
        webSetting.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // 是否允许获取WebView的内容URL ，可以让WebView访问ContentPrivider存储的内容。 默认true
        webSetting.setAllowContentAccess(true);
        //设置可以访问文件
        webSetting.setAllowFileAccess(true);
        webSetting.setSavePassword(false);
        //设置WebView是否保存表单数据，默认true，保存数据。
        webSetting.setSaveFormData(false);
        //支持自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
        //支持缩放，默认为true。是下面那个的前提
        webSetting.setSupportZoom(true);
        //设置内置的缩放控件。若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放
        webSetting.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSetting.setDisplayZoomControls(false);
        //支持多窗口
        webSetting.setSupportMultipleWindows(false);
        // 支持内容重新布局 图片过大时自动适应屏幕： 缩放排版:SINGLE_COLUMN 适应屏幕:NORMAL
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSetting.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSetting.setLoadWithOverviewMode(true);
        //不使用缓存
//        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
       /**
        * 从Android5.0以后，当一个安全的站点（https）去加载一个非安全的站点（http）时，需要配置Webview加载内容的混合模式，一共有如下三种模式：
        MIXED_CONTENT_NEVER_ALLOW：Webview不允许一个安全的站点（https）去加载非安全的站点内容（http）,比如，https网页内容的图片是http链接。强烈建议App使用这种模式，因为这样更安全。
        MIXED_CONTENT_ALWAYS_ALLOW：在这种模式下，WebView是可以在一个安全的站点（Https）里加载非安全的站点内容（Http）,这是WebView最不安全的操作模式，尽可能地不要使用这种模式。
        MIXED_CONTENT_COMPATIBILITY_MODE：在这种模式下，当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器的风格。一些不安全的内容（Http）能被加载到一个安全的站点上（Https），
        而其他类型的内容将会被阻塞。这些内容的类型是被允许加载还是被阻塞可能会随着版本的不同而改变，并没有明确的定义。这种模式主要用于在App里面不能控制内容的渲染，但是又希望在一个安全的环境下运行。

        在Android5.0以下，默认是采用的MIXED_CONTENT_ALWAYS_ALLOW模式，即总是允许WebView同时加载Https和Http；而从Android5.0开始，默认用MIXED_CONTENT_NEVER_ALLOW模式，即总是不允许WebView同时加载Https和Http
        * */
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public static Map<String, String> getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";// 匹配参数名和参数值的正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        while (m.find()) {
            String paramName = m.group(2);// 获取参数名
            String paramVal = m.group(3);// 获取参数值
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }


    public static String addExpend(String expand) {
        String[] s = null;
        if (expand.contains("#")){
            s = expand.split("#");
            expand = s[0];
        }
        KLog.e("expand:"+expand);
        if (TextUtils.isEmpty(Constants.sessionid))
            return expand;
        if (expand.contains(Constants.SESSION_ID)){
            Map<String, String> map = getUrlPramNameAndValue(expand);
            String sessionid = map.get(Constants.SESSION_ID);
            if (sessionid != null&&!"".equals(sessionid)) {
                if (!Constants.sessionid.equals(sessionid)) {
                    expand = expand.replace(sessionid, Constants.sessionid);
                }else {
                    return expand;
                }
            }else {
                return expand.trim() + Constants.sessionid;
            }
            return expand;
        }
        if (expand.contains("?")) {
            expand = expand + "&" + Constants.SESSION_ID + "=" + Constants.sessionid;
        } else {
            expand = expand + "?" + Constants.SESSION_ID + "=" + Constants.sessionid;
        }
        if (expand.contains("#") && s!=null){
            expand = expand + "#" +s[1];
        }
        return expand;
    }

    /**
     * 替换url中的参数值
     * @param url
     * @param name
     * @param accessToken
     * @return
     */
    public static String replaceAccessTokenReg(String url, String name, String accessToken) {
        if(!TextUtils.isEmpty(url) && !TextUtils.isEmpty(accessToken)&& !TextUtils.isEmpty(name)) {
            url = url.replaceAll("(" + name +"=[^&]*)", name + "=" + accessToken);
        }
        return url;
    }


    /**
     * 移除链接中的 参数
     * @param url  链接
     * @param params 参数的key组成的数组 例如 new String[]{"Sessionid","page"};
     * @return
     */
    public static String removeParams(String url, String[] params) {
        String reg = null;
        StringBuffer ps = new StringBuffer();
        ps.append("(");
        for(int i = 0; i < params.length; i++) {
            ps.append(params[i]).append("|");
        }
        ps.deleteCharAt(ps.length() - 1);
        ps.append(")");
        reg = "(?<=[\\?&])" + ps.toString() + "=[^&]*&?";
        url = url.replaceAll(reg, "");
        url = url.replaceAll("(\\?|&+)$", "");
        return url;
    }


}
