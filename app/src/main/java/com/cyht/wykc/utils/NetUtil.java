package com.cyht.wykc.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.cyht.wykc.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtil {
	/**
	 * 检查网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {

		boolean isWifiConnection = isWifiConnection(context);
		boolean isBaseStationConnection = isBaseStationConnection(context);

		if (!isWifiConnection && !isBaseStationConnection) {
//			Toast.makeText(context, context.getResources().getString(R.string.unconnect), Toast.LENGTH_SHORT).show();
			return false;
		}

		if (isBaseStationConnection) {
			// 读取代理信息
			@SuppressWarnings("deprecation")
            String ip = android.net.Proxy.getDefaultHost();
			if (!TextUtils.isEmpty(ip)) {
				// Constanc.isWap = true;
			}
		}
		return true;
	}

	/**
	 * 判断是否是基站联网
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isBaseStationConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断是否是wifi联网
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
	
	public static String getWiFiIpAddress(final Context context) {
		String ipAddress = null;
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			// ipAddress = info.geti;
			// IP地址必须在已连接状态下否则为0
			ipAddress = int2ip(info.getIpAddress());
		}

		return ipAddress;
	}


	/**
	 * 获取WIFI的ip地址,原生系统获取方式 返回本地IP地址
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
//			Log.e("WifiPreference IpAddress", ex.toString());
		}

		return null;
	}

	/**
	 * int型转换成IP地址
	 * 
	 * @param ipInt
	 * @return
	 */
	public static String int2ip(long ipInt) {
		StringBuilder sb = new StringBuilder();
		sb.append(ipInt & 0xFF).append(".");
		sb.append((ipInt >> 8) & 0xFF).append(".");
		sb.append((ipInt >> 16) & 0xFF).append(".");
		sb.append((ipInt >> 24) & 0xFF);
		return sb.toString();
	}

	/**
	 * IP地址转换成int型
	 * 
	 * @param ip
	 * @return
	 */
	public static long ip2int(String ip) {
		String[] items = ip.split("\\.");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}

	public static void showNetDialog(Context context){
		new AlertDialog.Builder(context)
				.setMessage(R.string.dialog_net_error)
				.setNegativeButton(R.string.dialog_connect_ok, null)
				.show();
	}
}
