package com.cyht.wykc.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 存储空间管理
 * 
 * @author
 * 
 */
public class StoreSpaceUtils {
	
	/**
	 * 获取默认存储路径
	 * @return
	 */
	public static String getDefaultPath(){
		if(isExistSDCard()){
			return getSDCardPath();
		}else{
			return Environment.getExternalStorageDirectory().getPath();
		}
	}
	

	/**
	 * 计算总空间
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static long getTotalSize(String path) {
		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getBlockCount() * fileStats.getBlockSize();
	}
	
	/**
	 * 计算剩余空间
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getAvailableSize(String path) {
		if (path == null) {
			return 0;
		}

		StatFs fileStats = new StatFs(path);
		fileStats.restat(path);
		return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
	}

	/**
	 * 获取系统可读写的总空间
	 * 
	 * @return
	 */
	public static long getSysTotalSize() {
		return getTotalSize("/data");
	}

	/**
	 * 计算系统的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getSystemAvailableSize() {
		return getAvailableSize("/data");
	}
	
	/**
	 * 判断sd卡是否存在
	 * @return
	 */
	public static boolean isExistSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	}

	/**
	 * 获取sd卡的路径
	 * @return
	 */
	public static String getSDCardPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		
		if(sdDir == null){
			return null;
		}
		
		return sdDir.toString();
	}
	
	/**
	 * 获取SD卡的总空间
	 * 
	 * @return
	 */
	public static long getSDTotalSize() {
		if (isExistSDCard()) {
			return getTotalSize(getSDCardPath());
		}

		return 0;
	}
	

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 剩余空间
	 */
	public static long getSDAvailableSize() {
		if (isExistSDCard()) {
			return getAvailableSize(getSDCardPath());
		}

		return 0;
	}
}
