package com.cyht.wykc.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class FileUtils {
//	private static final String TAG = "FileUtils";
	private static String SDCardRoot; // /mnt/sdcard

	public static String getSDCardRoot() {
		return SDCardRoot;
	}

	static {
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
	}



//	/** 根据文件后缀获取图标 */
//	public static int getFileTypeImg(String suffix) {
//		suffix = suffix.toLowerCase();
//		if (suffix.equals(".doc") || suffix.equals(".docx")) {
//			return R.drawable.doc;
//		} else if (suffix.equals(".html")) {
//			return R.drawable.html;
//		} else if (suffix.equals(".pdf")) {
//			return R.drawable.pdf;
//		} else if (suffix.equals(".ppt")) {
//			return R.drawable.ppt;
//		} else if (suffix.equals(".txt")) {
//			return R.drawable.txt;
//		} else if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
//			return R.drawable.xls;
//		} else if (suffix.equals(".zip") || suffix.equals(".rar")) {
//			return R.drawable.zip;
//		} else if (suffix.equals(".apk")) {
//			return R.drawable.apk;
//		} else if (suffix.equals(".mp3") || suffix.equals(".wav")
//				|| suffix.equals(".wma") || suffix.equals(".ape")
//				|| suffix.equals(".mid") || suffix.equals(".amr")) {
//			return R.drawable.mp3;
//		} else if (suffix.equals(".avi") || suffix.equals(".rmvb")
//				|| suffix.equals(".wma") || suffix.equals(".3gp")
//				|| suffix.equals(".rm") || suffix.equals(".mid")
//				|| suffix.equals(".flash")) {
//			return R.drawable.fvideo;
//		} else {
//			return R.drawable.other;
//		}
//	}
	
//	/**
//	 * 得到目录文件的大小
//	 * @param file
//	 * @return
//	 */
//	public static double getDirSize(File file) {     
//        //判断文件是否存在     
//        if (file.exists()) {     
//            //如果是目录则递归计算其内容的总大小    
//            if (file.isDirectory()) {     
//                File[] children = file.listFiles();     
//                double size = 0;     
//                for (File f : children)     
//                    size += getDirSize(f);     
//                return size;     
//            } else {//如果是文件则直接返回其大小,以“兆”为单位   
////                double size = (double) file.length() / 1024 / 1024;        
//            	double size = file.length();
//                return size;     
//            }     
//        } else {     
//            return 0.0;     
//        }     
//    }

	/**
	 * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePathFromUri(final Context context, final Uri uri) {
		if (null == uri) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	
	   /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
    	if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 保存文件到固定目录
	 * 
	 * @param cacheFile
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String saveFileToExistDir(File cacheFile, String filePath) throws Exception {

		HttpURLConnection httpUrl = null;
		InputStream in = null;
		File file = null;
		DataInputStream dataIn = null;
		byte data[] = null;
		FileOutputStream fos = null;
		UrlConnectionPool connectPool = UrlConnectionPool.getInstanceConnectPool();

		try {
			if (filePath == null) {
				return null;
			}

			if (cacheFile.exists() == false) {
				cacheFile.mkdirs();
			}
			file = new File(cacheFile, getFileNameByPath(filePath));
			if (file.exists()) {
				return file.getPath();
			}

			// httpUrl =
			// (HttpURLConnection)UtilTools.getURLConnection(filePath);
			httpUrl = connectPool.addConnect(filePath);
			if (httpUrl.getResponseCode() != 200) {
				throw new Exception(httpUrl.getResponseMessage() + "-----" + httpUrl.getResponseCode() + "   filePath == " + filePath);
			}

			dataIn = new DataInputStream(httpUrl.getInputStream());

			// 判断SDCard的空间
			long fileLen = dataIn.available();
			long freeSize = getSDFreeSize();
			if (freeSize < fileLen) {
				long tempLen = getFileDirSize(cacheFile) + freeSize;

				if (fileLen > tempLen) {
					return filePath;
				}
				clearFileSpace(cacheFile, fileLen);
			}
			file.createNewFile();
			data = new byte[1024];
			fos = new FileOutputStream(file);
			int len = 0;

			while ((len = dataIn.read(data)) != -1) {
				fos.write(data, 0, len);
			}

			return file.getPath();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (dataIn != null) {
				dataIn.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (connectPool != null) {
				connectPool.remove(httpUrl);
			}
		}
	}
	
	/**
	 * 获得SDCard的空闲大小
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getSDFreeSize() {

		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());

		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();

		// 返回SD卡空闲大小
		return freeBlocks * blockSize;// 单位Byte
	}
	
	/**
	 * 得到目录下所有文件的大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileDirSize(File filePath) {

		if (filePath == null) {
			return 0;
		}

		List<File> fileList = new ArrayList<File>();
		traverseFile(filePath, fileList);

		long fileSize = 0;
		File file = null;
		for (int i = 0; i < fileList.size(); i++) {

			file = fileList.get(i);

			fileSize = fileSize + file.length();
		}

		return fileSize;
	}
	
	/**
	 * 在目录下清理出固定大小的空间，将创建最早的文件清理掉
	 * 
	 * @param filePath
	 * @param clearSize
	 */
	public static long clearFileSpace(File filePath, long clearSize) {

		long fileSize = 0;
		long currentTime = System.currentTimeMillis();

		List<File> fileList = new ArrayList<File>();
		traverseFile(filePath, fileList);

		// 安装创建时间排序
		Collections.sort(fileList, new Comparator<File>() {

			@Override
			public int compare(File file1, File file2) {

				if (file1.lastModified() > file2.lastModified()) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		File file = null;
		for (int i = 0; i < fileList.size(); i++) {

			file = fileList.get(i);

			fileSize = fileSize + file.length();

			if (fileSize > clearSize) {
				break;
			}

			if (currentTime < file.lastModified()) {
				break;
			}

			file.delete();
		}

		return fileSize;
	}
	
	/**
	 * 判断存储空间是否够用
	 * 
	 * @param length
	 * @return
	 */
	public static boolean isCanDownload(String path, long length) {
		long keepSize = 300 * 1024 * 1024;
		long availableSize = 0;

		clearFileSpace(new File(path), length);
		if (StoreSpaceUtils.isExistSDCard()) {
			availableSize = StoreSpaceUtils.getSDAvailableSize();
			keepSize = 200 * 1024 * 1024;
		} else {
			availableSize = StoreSpaceUtils.getSystemAvailableSize();
		}

		if (length > (availableSize - keepSize)) {
			return false;
		}

		return true;
	}
	
	public static List<String> getDirChildFilePaths(String dirPath, int start, int end) {

		if (dirPath == null || dirPath.trim().length() == 0) {
			return null;
		}

		List<String> uriList = new ArrayList<String>();

		File file = new File(dirPath);

		if (file.exists() == false || file.isFile()) {
			uriList.add(dirPath);
			return uriList;
		}

		if (file.isDirectory() == false) {
			return uriList;
		}

		List<String> fileFormats = new ArrayList<String>();
		List<String> list = fileSort(file.listFiles(), fileFormats);
		if (list == null || list.isEmpty()) {
			return null;
		}

		for (int i = start; i < end; i++) {
			uriList.add(dirPath + "/" + list.get(i) + "." + fileFormats.get(i));
		}

		return uriList;
	}

	public static List<String> fileSort(File childFiles[], List<String> fileFormats) {
		if (childFiles == null) {
			return null;
		}

		List<String> list = new ArrayList<String>();
		String tempPath = null;
		String tempName = null;
		for (int i = 0; i < childFiles.length; i++) {
			tempPath = childFiles[i].getPath();
			tempName = tempPath.substring(tempPath.lastIndexOf("/") + 1, tempPath.lastIndexOf("."));
			if (tempName.matches("\\d+") == false) {
				continue;
			}

			fileFormats.add(tempPath.substring(tempPath.lastIndexOf(".") + 1));
			list.add(tempName);
		}

		stringListSort(list);

		return list;
	}
	
	public static void stringListSort(List<String> list) {

		if (list == null || list.isEmpty()) {
			return;
		}

		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String object1, String object2) {
				try {
					if (object1 == null || object2 == null) {
						return 0;
					}

					BigInteger t1 = new BigInteger(object1.toString());
					BigInteger t2 = new BigInteger(object2.toString());

					return t1.subtract(t2).intValue();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return 0;
			}
		});
	}
	
	/**
	 * 遍历文件
	 * 
	 * @param filePath
	 * @param fileList
	 */
	public static void traverseFile(File filePath, List<File> fileList) {

		if (filePath == null) {
			return;
		}

		if (fileList == null) {
			fileList = new ArrayList<File>();
		}

		File files[] = filePath.listFiles();

		if (files == null) {
			return;
		}

		fileList.add(filePath);

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				fileList.add(files[i]);
			}

			traverseFile(files[i], fileList);
		}
	}
	
	/**
	 * 根据文件的路径，得到文件的名称
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameByPath(String filePath) {

		if (filePath == null || filePath.trim().length() == 0) {
			return null;
		}

		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/** 返回文件类型 , 1代表文档,2代表图片,3代表影音,0代表其她 */
	public static int getFileType(String suffix) {
		suffix = suffix.toLowerCase(Locale.getDefault());
		if (suffix.equals(".doc") || suffix.equals(".docx")) {
			return 1;
		} else if (suffix.equals(".html")) {
			return 0;
		} else if (suffix.equals(".pdf")) {
			return 1;
		} else if (suffix.equals(".ppt")) {
			return 1;
		} else if (suffix.equals(".txt")) {
			return 1;
		} else if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
			return 1;
		} else if (suffix.equals(".zip") || suffix.equals(".rar")) {
			return 0;
		} else if (suffix.equals(".apk")) {
			return 0;
		} else if (suffix.equals(".mp3") || suffix.equals(".wav")
				|| suffix.equals(".wma") || suffix.equals(".ape")
				|| suffix.equals(".mid") || suffix.equals(".amr")) {
			return 3;
		} else if (suffix.equals(".avi") || suffix.equals(".rmvb")
				|| suffix.equals(".wma") || suffix.equals(".3gp")
				|| suffix.equals(".rm") || suffix.equals(".mid")
				|| suffix.equals(".flash")) {
			return 3;
		} else if (suffix.equals(".bmp") || suffix.equals(".png")
				|| suffix.equals(".pcx") || suffix.equals(".gif")
				|| suffix.equals(".jpeg") || suffix.equals(".tiff")
				|| suffix.equals(".dxf") || suffix.equals(".exif")
				|| suffix.equals(".jpg")) {
			return 2;
		} else {
			return 0;
		}
	}

	/** 复制文件 */
	public static void copyfile(File fromFile, File toFile, Boolean rewrite) {
		if (!fromFile.exists()) {
			return;
		}
		if (!fromFile.isFile()) {
			return;
		}
		if (!fromFile.canRead()) {
			return;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}

		try {
			FileInputStream fosfrom = new FileInputStream(
					fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[102400];
			int c;

			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			fosfrom.close();
			fosto.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 读取目录中的Photo
	 */
//	public static ArrayList<Photo> getFiles(String path) {
//		ArrayList<Photo> photos = new ArrayList<Photo>();
//		File file = new File(SDCardRoot + File.separator + path);
//		LogUtil.i(TAG, SDCardRoot + File.separator + path);
//		File[] files = file.listFiles(new FileFilter() {
//			public boolean accept(File pathname) {
//				return pathname.getName().endsWith(".png");
//			}
//		});
//		for (int i = 0; i < files.length; i++) {
//			File f = files[i];
//			Photo photo = new Photo();
//			photo.setName(f.getName());
//			photo.setUri(Uri.parse("file://" + f.getAbsolutePath()));
//			photos.add(photo);
//		}
//
//		LogUtil.i(TAG, photos.toString());
//		return photos;
//	}

//	public static ArrayList<Photo> delete(String path) {
//		ArrayList<Photo> photos = new ArrayList<Photo>();
//		File file = new File(SDCardRoot + File.separator + path);
//		LogUtil.i(TAG, SDCardRoot + File.separator + path);
//		File[] files = file.listFiles(new FileFilter() {
//			public boolean accept(File pathname) {
//				return pathname.getName().endsWith(".png");
//			}
//		});
//		for (int i = 0; i < files.length; i++) {
//			File f = files[i];
//			Photo photo = new Photo();
//			photo.setName(f.getName());
//			photo.setUri(Uri.parse("file://" + f.getAbsolutePath()));
//			f.delete();
//			photos.add(photo);
//		}
//		return photos;
//	}

	/**
	 * 获取文件类型
	 */
	public static String getMIMEType(String filePath) {
		String type = "*/*";
		String fName = filePath;
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
		if (end == "") {
			return type;
		}

		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0])) {
				type = MIME_MapTable[i][1];
			}
		}
		return type;
	}

	/**
	 * 返回文件后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String FileExt(String filename) {
		int k = filename.lastIndexOf(".");
		if (k != -1) {
			String ext = filename.substring(k);
			return ext;
		} else {
			return "";
		}
	}



//	/**
//	 * 获取未安装的apk信息
//	 *
//	 * @param ctx
//	 *            Context
//	 * @param apkPath
//	 *            apk路径，可以放在SD卡
//	 * @return
//	 */
//	public static AppInfoData getApkFileInfo(Context ctx, String apkPath) {
//		System.out.println(apkPath);
//		File apkFile = new File(apkPath);
//		if (!apkFile.exists() || !apkPath.toLowerCase().endsWith(".apk")) {
//			System.out.println("file path is not correct");
//			return null;
//		}
//
//		AppInfoData appInfoData;
//		String PATH_PackageParser = "android.content.pm.PackageParser";
//		String PATH_AssetManager = "android.content.res.AssetManager";
//		try {
//			// 反射得到pkgParserCls对象并实例化,有参数
//			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
//			Class<?>[] typeArgs = { String.class };
//			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
//			Object[] valueArgs = { apkPath };
//			Object pkgParser = pkgParserCt.newInstance(valueArgs);
//
//			// 从pkgParserCls类得到parsePackage方法
//			DisplayMetrics metrics = new DisplayMetrics();
//			metrics.setToDefaults();// 这个是与显示有关的, 这边使用默认
//			typeArgs = new Class<?>[] { File.class, String.class,
//					DisplayMetrics.class, int.class };
//			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
//					"parsePackage", typeArgs);
//
//			valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };
//
//			// 执行pkgParser_parsePackageMtd方法并返回
//			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
//					valueArgs);
//
//			// 从返回的对象得到名为"applicationInfo"的字段对象
//			if (pkgParserPkg == null) {
//				return null;
//			}
//			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
//					"applicationInfo");
//
//			// 从对象"pkgParserPkg"得到字段"appInfoFld"的值
//			if (appInfoFld.get(pkgParserPkg) == null) {
//				return null;
//			}
//			ApplicationInfo info = (ApplicationInfo) appInfoFld
//					.get(pkgParserPkg);
//
//			// 反射得到assetMagCls对象并实例化,无参
//			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
//			Object assetMag = assetMagCls.newInstance();
//			// 从assetMagCls类得到addAssetPath方法
//			typeArgs = new Class[1];
//			typeArgs[0] = String.class;
//			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
//					"addAssetPath", typeArgs);
//			valueArgs = new Object[1];
//			valueArgs[0] = apkPath;
//			// 执行assetMag_addAssetPathMtd方法
//			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
//
//			// 得到Resources对象并实例化,有参数
//			Resources res = ctx.getResources();
//			typeArgs = new Class[3];
//			typeArgs[0] = assetMag.getClass();
//			typeArgs[1] = res.getDisplayMetrics().getClass();
//			typeArgs[2] = res.getConfiguration().getClass();
//			Constructor<Resources> resCt = Resources.class
//					.getConstructor(typeArgs);
//			valueArgs = new Object[3];
//			valueArgs[0] = assetMag;
//			valueArgs[1] = res.getDisplayMetrics();
//			valueArgs[2] = res.getConfiguration();
//			// 这个是重点
//			// 得到Resource对象后可以有很多用处
//			res = (Resources) resCt.newInstance(valueArgs);
//
//			// 读取apk文件的信息
//			appInfoData = new AppInfoData();
//			if (info != null) {
//				if (info.icon != 0) {
//					// 图片存在，则读取相关信息
//					Drawable icon = res.getDrawable(info.icon);// 图标
//					appInfoData.setAppicon(icon);
//				}
//				if (info.labelRes != 0) {
//					String neme = (String) res.getText(info.labelRes);// 名字
//					appInfoData.setAppname(neme);
//				} else {
//					String apkName = apkFile.getName();
//					appInfoData.setAppname(apkName.substring(0,
//							apkName.lastIndexOf(".")));
//				}
//				String pkgName = info.packageName;// 包名
//				appInfoData.setApppackage(pkgName);
//			} else {
//				return null;
//			}
//
//			return appInfoData;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	// 安装apk
	protected void installApk(Context context, File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	public static String getFilesize(String filesize) {
		int filesize1 = Integer.valueOf(filesize);
		float filesize2 = filesize1;
		String memoryUnit = "";
		if (filesize1 < 1024) {
			memoryUnit = "B";
		} else if (filesize1 >= 1024 && filesize1 < 1024 * 1024) {
			memoryUnit = "KB";
		} else if (filesize1 >= 1024 * 1024 && filesize1 < 1024 * 1024 * 1024) {
			memoryUnit = "MB";
		} else if (filesize1 >= 1024 * 1024 * 1024
				&& filesize1 < 1024 * 1024 * 1024 * 1024) {
			memoryUnit = "GB";
		}
		if ("KB".equals(memoryUnit)) {
			filesize2 = ((float) filesize1) / 1024;
		} else if ("MB".equals(memoryUnit)) {
			filesize2 = ((float) filesize1) / 1024 / 1024;
		} else if ("GB".equals(memoryUnit)) {
			filesize2 = ((float) filesize1) / 1024 / 1024 / 1024;
		}
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String dd = fnum.format(filesize2);
		return dd + " " + memoryUnit;
	}

	/**
	 * 获取文件的MD5值
	 */
	public static String getFileMD5(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		String guid = bigInt.toString(16);
		if (guid.length() == 32) {
			guid = guid.substring(0, 8) + "-" + guid.substring(8, 12) + "-"
					+ guid.substring(12, 16) + "-" + guid.substring(16, 20)
					+ "-" + guid.substring(20);
		}
		return guid;
	}

	/**
	 * MIME 列表
	 */
	public static final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" }, { ".3gpp", "video/3gpp" },
			{ ".aac", "audio/x-mpeg" }, { ".amr", "audio/x-mpeg" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".avi", "video/x-msvideo" },
			{ ".aab", "application/x-authoware-bin" },
			{ ".aam", "application/x-authoware-map" },
			{ ".aas", "application/x-authoware-seg" },
			{ ".ai", "application/postscript" }, { ".aif", "audio/x-aiff" },
			{ ".aifc", "audio/x-aiff" }, { ".aiff", "audio/x-aiff" },
			{ ".als", "audio/X-Alpha5" }, { ".amc", "application/x-mpeg" },
			{ ".ani", "application/octet-stream" }, { ".asc", "text/plain" },
			{ ".asd", "application/astound" }, { ".asf", "video/x-ms-asf" },
			{ ".asn", "application/astound" },
			{ ".asp", "application/x-asap" }, { ".asx", " video/x-ms-asf" },
			{ ".au", "audio/basic" }, { ".avb", "application/octet-stream" },
			{ ".awb", "audio/amr-wb" }, { ".bcpio", "application/x-bcpio" },
			{ ".bld", "application/bld" }, { ".bld2", "application/bld2" },
			{ ".bpk", "application/octet-stream" },
			{ ".bz2", "application/x-bzip2" },
			{ ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".cal", "image/x-cals" }, { ".ccn", "application/x-cnc" },
			{ ".cco", "application/x-cocoa" },
			{ ".cdf", "application/x-netcdf" },
			{ ".cgi", "magnus-internal/cgi" },
			{ ".chat", "application/x-chat" },
			{ ".clp", "application/x-msclip" },
			{ ".cmx", "application/x-cmx" },
			{ ".co", "application/x-cult3d-object" },
			{ ".cod", "image/cis-cod" }, { ".cpio", "application/x-cpio" },
			{ ".cpt", "application/mac-compactpro" },
			{ ".crd", "application/x-mscardfile" },
			{ ".csh", "application/x-csh" }, { ".csm", "chemical/x-csml" },
			{ ".csml", "chemical/x-csml" }, { ".css", "text/css" },
			{ ".cur", "application/octet-stream" },
			{ ".doc", "application/msword" }, { ".dcm", "x-lml/x-evm" },
			{ ".docx", "application/msword" },
			{ ".dcr", "application/x-director" }, { ".dcx", "image/x-dcx" },
			{ ".dhtml", "text/html" }, { ".dir", "application/x-director" },
			{ ".dll", "application/octet-stream" },
			{ ".dmg", "application/octet-stream" },
			{ ".dms", "application/octet-stream" },
			{ ".dot", "application/x-dot" }, { ".dvi", "application/x-dvi" },
			{ ".dwf", "drawing/x-dwf" }, { ".dwg", "application/x-autocad" },
			{ ".dxf", "application/x-autocad" },
			{ ".dxr", "application/x-director" },
			{ ".ebk", "application/x-expandedbook" },
			{ ".emb", "chemical/x-embl-dl-nucleotide" },
			{ ".embl", "chemical/x-embl-dl-nucleotide" },
			{ ".eps", "application/postscript" },
			{ ".epub", "application/epub+zip" }, { ".eri", "image/x-eri" },
			{ ".es", "audio/echospeech" }, { ".esl", "audio/echospeech" },
			{ ".etc", "application/x-earthtime" }, { ".etx", "text/x-setext" },
			{ ".evm", "x-lml/x-evm" }, { ".evy", "application/x-envoy" },
			{ ".exe", "application/octet-stream" },
			{ ".fh4", "image/x-freehand" }, { ".fh5", "image/x-freehand" },
			{ ".fhc", "image/x-freehand" }, { ".fif", "image/fif" },
			{ ".fm", "application/x-maker" }, { ".fpx", "image/x-fpx" },
			{ ".fvi", "video/isivideo" }, { ".flv", "video/x-msvideo" },
			{ ".gau", "chemical/x-gaussian-input" },
			{ ".gca", "application/x-gca-compressed" },
			{ ".gdb", "x-lml/x-gdb" }, { ".gif", "image/gif" },
			{ ".gps", "application/x-gps" }, { ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" },
			{ ".h", "text/plain" }, { ".hdf", "application/x-hdf" },
			{ ".hdm", "text/x-hdml" }, { ".hdml", "text/x-hdml" },
			{ ".htm", "text/html" }, { ".html", "text/html" },
			{ ".hlp", "application/winhlp" },
			{ ".hqx", "application/mac-binhex40" }, { ".hts", "text/html" },
			{ ".ice", "x-conference/x-cooltalk" },
			{ ".ico", "application/octet-stream" }, { ".ief", "image/ief" },
			{ ".ifm", "image/gif" }, { ".ifs", "image/ifs" },
			{ ".imy", "audio/melody" },
			{ ".ins", "application/x-NET-Install" },
			{ ".ips", "application/x-ipscript" },
			{ ".ipx", "application/x-ipix" }, { ".it", "audio/x-mod" },
			{ ".itz", "audio/x-mod" }, { ".ivr", "i-world/i-vrml" },
			{ ".j2k", "image/j2k" },
			{ ".jad", "text/vnd.sun.j2me.app-descriptor" },
			{ ".jam", "application/x-jam" },
			{ ".jnlp", "application/x-java-jnlp-file" },
			{ ".jpe", "image/jpeg" }, { ".jpz", "image/jpeg" },
			{ ".jwc", "application/jwc" },
			{ ".jar", "application/java-archive" }, { ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".kjx", "application/x-kjx" }, { ".lak", "x-lml/x-lak" },
			{ ".latex", "application/x-latex" },
			{ ".lcc", "application/fastman" },
			{ ".lcl", "application/x-digitalloca" },
			{ ".lcr", "application/x-digitalloca" },
			{ ".lgh", "application/lgh" },
			{ ".lha", "application/octet-stream" }, { ".lml", "x-lml/x-lml" },
			{ ".lmlpack", "x-lml/x-lmlpack" }, { ".log", "text/plain" },
			{ ".lsf", "video/x-ms-asf" }, { ".lsx", "video/x-ms-asf" },
			{ ".lzh", "application/x-lzh " },
			{ ".m13", "application/x-msmediaview" },
			{ ".m14", "application/x-msmediaview" }, { ".m15", "audio/x-mod" },
			{ ".m3u", "audio/x-mpegurl" }, { ".m3url", "audio/x-mpegurl" },
			{ ".ma1", "audio/ma1" }, { ".ma2", "audio/ma2" },
			{ ".ma3", "audio/ma3" }, { ".ma5", "audio/ma5" },
			{ ".man", "application/x-troff-man" },
			{ ".map", "magnus-internal/imagemap" },
			{ ".mbd", "application/mbedlet" },
			{ ".mct", "application/x-mascot" },
			{ ".mdb", "application/x-msaccess" }, { ".mdz", "audio/x-mod" },
			{ ".me", "application/x-troff-me" }, { ".mel", "text/x-vmel" },
			{ ".mi", "application/x-mif" }, { ".mid", "audio/midi" },
			{ ".midi", "audio/midi" }, { ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".mif", "application/x-mif" }, { ".mil", "image/x-cals" },
			{ ".mio", "audio/x-mio" }, { ".mmf", "application/x-skt-lbs" },
			{ ".mng", "video/x-mng" }, { ".mny", "application/x-msmoney" },
			{ ".moc", "application/x-mocha" },
			{ ".mocha", "application/x-mocha" }, { ".mod", "audio/x-mod" },
			{ ".mof", "application/x-yumekara" },
			{ ".mol", "chemical/x-mdl-molfile" },
			{ ".mop", "chemical/x-mopac-input" },
			{ ".movie", "video/x-sgi-movie" },
			{ ".mpn", "application/vnd.mophun.application" },
			{ ".mpp", "application/vnd.ms-project" },
			{ ".mps", "application/x-mapserver" }, { ".mrl", "text/x-mrml" },
			{ ".mrm", "application/x-mrm" },
			{ ".ms", "application/x-troff-ms" },
			{ ".mts", "application/metastream" },
			{ ".mtx", "application/metastream" },
			{ ".mtz", "application/metastream" },
			{ ".mzv", "application/metastream" },
			{ ".nar", "application/zip" }, { ".nbmp", "image/nbmp" },
			{ ".nc", "application/x-netcdf" }, { ".ndb", "x-lml/x-ndb" },
			{ ".ndwn", "application/ndwn" }, { ".nif", "application/x-nif" },
			{ ".nmz", "application/x-scream" },
			{ ".nokia-op-logo", "image/vnd.nok-oplogo-color" },
			{ ".npx", "application/x-netfpx" }, { ".nsnd", "audio/nsnd" },
			{ ".nva", "application/x-neva1" }, { ".oda", "application/oda" },
			{ ".oom", "application/x-AtlasMate-Plugin" },
			{ ".ogg", "audio/ogg" }, { ".pac", "audio/x-pac" },
			{ ".pae", "audio/x-epac" }, { ".pan", "application/x-pan" },
			{ ".pbm", "image/x-portable-bitmap" }, { ".pcx", "image/x-pcx" },
			{ ".pda", "image/x-pda" }, { ".pdb", "chemical/x-pdb" },
			{ ".pdf", "application/pdf" },
			{ ".pfr", "application/font-tdpfr" },
			{ ".pgm", "image/x-portable-graymap" },
			{ ".pict", "image/x-pict" }, { ".pm", "application/x-perl" },
			{ ".pmd", "application/x-pmd" }, { ".png", "image/png" },
			{ ".pnm", "image/x-portable-anymap" }, { ".pnz", "image/png" },
			{ ".pot", "application/vnd.ms-powerpoint" },
			{ ".ppm", "image/x-portable-pixmap" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pqf", "application/x-cprplayer" },
			{ ".pqi", "application/cprplayer" },
			{ ".prc", "application/x-prc" },
			{ ".proxy", "application/x-ns-proxy-autoconfig" },
			{ ".prop", "text/plain" }, { ".ps", "application/postscript" },
			{ ".ptlk", "application/listenup" },
			{ ".pub", "application/x-mspublisher" },
			{ ".pvx", "video/x-pv-pvx" }, { ".qcp", "audio/vnd.qcelp" },
			{ ".qt", "video/quicktime" }, { ".qti", "image/x-quicktime" },
			{ ".qtif", "image/x-quicktime" },
			{ ".r3t", "text/vnd.rn-realtext3d" },
			{ ".ra", "audio/x-pn-realaudio" },
			{ ".ram", "audio/x-pn-realaudio" },
			{ ".ras", "image/x-cmu-raster" },
			{ ".rdf", "application/rdf+xml" },
			{ ".rf", "image/vnd.rn-realflash" }, { ".rgb", "image/x-rgb" },
			{ ".rlf", "application/x-richlink" },
			{ ".rm", "audio/x-pn-realaudio" }, { ".rmf", "audio/x-rmf" },
			{ ".rmm", "audio/x-pn-realaudio" },
			{ ".rnx", "application/vnd.rn-realplayer" },
			{ ".roff", "application/x-troff" },
			{ ".rp", "image/vnd.rn-realpix" },
			{ ".rpm", "audio/x-pn-realaudio-plugin" },
			{ ".rt", "text/vnd.rn-realtext" }, { ".rte", "x-lml/x-gps" },
			{ ".rtf", "application/rtf" },
			{ ".rtg", "application/metastream" }, { ".rtx", "text/richtext" },
			{ ".rv", "video/vnd.rn-realvideo" },
			{ ".rwc", "application/x-rogerwilco" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".s3m", "audio/x-mod" }, { ".s3z", "audio/x-mod" },
			{ ".sca", "application/x-supercard" },
			{ ".scd", "application/x-msschedule" },
			{ ".sdf", "application/e-score" },
			{ ".sea", "application/x-stuffit" }, { ".sgm", "text/x-sgml" },
			{ ".sgml", "text/x-sgml" }, { ".shar", "application/x-shar" },
			{ ".shtml", "magnus-internal/parsed-html" },
			{ ".shw", "application/presentations" }, { ".si6", "image/si6" },
			{ ".si7", "image/vnd.stiwap.sis" },
			{ ".si9", "image/vnd.lgtwap.sis" },
			{ ".sis", "application/vnd.symbian.install" },
			{ ".sit", "application/x-stuffit" },
			{ ".skd", "application/x-Koan" }, { ".skm", "application/x-Koan" },
			{ ".skp", "application/x-Koan" }, { ".skt", "application/x-Koan" },
			{ ".slc", "application/x-salsa" }, { ".smd", "audio/x-smd" },
			{ ".smi", "application/smil" }, { ".smil", "application/smil" },
			{ ".smp", "application/studiom" }, { ".smz", "audio/x-smd" },
			{ ".sh", "application/x-sh" }, { ".snd", "audio/basic" },
			{ ".spc", "text/x-speech" },
			{ ".spl", "application/futuresplash" },
			{ ".spr", "application/x-sprite" },
			{ ".sprite", "application/x-sprite" },
			{ ".sdp", "application/sdp" }, { ".spt", "application/x-spt" },
			{ ".src", "application/x-wais-source" },
			{ ".stk", "application/hyperstudio" }, { ".stm", "audio/x-mod" },
			{ ".sv4cpio", "application/x-sv4cpio" },
			{ ".sv4crc", "application/x-sv4crc" }, { ".svf", "image/vnd" },
			{ ".svg", "image/svg-xml" }, { ".svh", "image/svh" },
			{ ".svr", "x-world/x-svr" },
			{ ".swf", "application/x-shockwave-flash" },
			{ ".swfl", "application/x-shockwave-flash" },
			{ ".t", "application/x-troff" },
			{ ".tad", "application/octet-stream" },
			{ ".talk", "text/x-speech" }, { ".tar", "application/x-tar" },
			{ ".taz", "application/x-tar" },
			{ ".tbp", "application/x-timbuktu" },
			{ ".tbt", "application/x-timbuktu" },
			{ ".tcl", "application/x-tcl" }, { ".tex", "application/x-tex" },
			{ ".texi", "application/x-texinfo" },
			{ ".texinfo", "application/x-texinfo" },
			{ ".tgz", "application/x-tar" },
			{ ".thm", "application/vnd.eri.thm" }, { ".tif", "image/tiff" },
			{ ".tiff", "image/tiff" }, { ".tki", "application/x-tkined" },
			{ ".tkined", "application/x-tkined" },
			{ ".toc", "application/toc" }, { ".toy", "image/toy" },
			{ ".tr", "application/x-troff" }, { ".trk", "x-lml/x-gps" },
			{ ".trm", "application/x-msterminal" },
			{ ".tsi", "audio/tsplayer" }, { ".tsp", "application/dsptype" },
			{ ".tsv", "text/tab-separated-values" },
			{ ".ttf", "application/octet-stream" },
			{ ".ttz", "application/t-time" }, { ".txt", "text/plain" },
			{ ".ult", "audio/x-mod" }, { ".ustar", "application/x-ustar" },
			{ ".uu", "application/x-uuencode" },
			{ ".uue", "application/x-uuencode" },
			{ ".vcd", "application/x-cdlink" }, { ".vcf", "text/x-vcard" },
			{ ".vdo", "video/vdo" }, { ".vib", "audio/vib" },
			{ ".viv", "video/vivo" }, { ".vivo", "video/vivo" },
			{ ".vmd", "application/vocaltec-media-desc" },
			{ ".vmf", "application/vocaltec-media-file" },
			{ ".vmi", "application/x-dreamcast-vms-info" },
			{ ".vms", "application/x-dreamcast-vms" },
			{ ".vox", "audio/voxware" }, { ".vqe", "audio/x-twinvq-plugin" },
			{ ".vqf", "audio/x-twinvq" }, { ".vql", "audio/x-twinvq" },
			{ ".vre", "x-world/x-vream" }, { ".vrml", "x-world/x-vrml" },
			{ ".vrt", "x-world/x-vrt" }, { ".vrw", "x-world/x-vream" },
			{ ".vts", "workbook/formulaone" }, { ".wax", "audio/x-ms-wax" },
			{ ".wbmp", "image/vnd.wap.wbmp" },
			{ ".web", "application/vnd.xara" }, { ".wav", "audio/x-wav" },
			{ ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" },
			{ ".wi", "image/wavelet" },
			{ ".wis", "application/x-InstallShield" },
			{ ".wm", "video/x-ms-wm" }, { ".wmd", "application/x-ms-wmd" },
			{ ".wmf", "application/x-msmetafile" },
			{ ".wml", "text/vnd.wap.wml" },
			{ ".wmlc", "application/vnd.wap.wmlc" },
			{ ".wmls", "text/vnd.wap.wmlscript" },
			{ ".wmlsc", "application/vnd.wap.wmlscriptc" },
			{ ".wmlscript", "text/vnd.wap.wmlscript" },
			{ ".wmv", "video/x-ms-wmv" }, { ".wmx", "video/x-ms-wmx" },
			{ ".wmz", "application/x-ms-wmz" }, { ".wpng", "image/x-up-wpng" },
			{ ".wps", "application/vnd.ms-works" }, { ".wpt", "x-lml/x-gps" },
			{ ".wri", "application/x-mswrite" }, { ".wrl", "x-world/x-vrml" },
			{ ".wrz", "x-world/x-vrml" }, { ".ws", "text/vnd.wap.wmlscript" },
			{ ".wsc", "application/vnd.wap.wmlscriptc" },
			{ ".wv", "video/wavelet" }, { ".wvx", "video/x-ms-wvx" },
			{ ".wxl", "application/x-wxl" },
			{ ".x-gzip", "application/x-gzip" },
			{ ".xar", "application/vnd.xara" }, { ".xbm", "image/x-xbitmap" },
			{ ".xdm", "application/x-xdma" },
			{ ".xdma", "application/x-xdma" },
			{ ".xdw", "application/vnd.fujixerox.docuworks" },
			{ ".xht", "application/xhtml+xml" },
			{ ".xhtm", "application/xhtml+xml" },
			{ ".xhtml", "application/xhtml+xml" },
			{ ".xla", "application/vnd.ms-excel" },
			{ ".xlc", "application/vnd.ms-excel" },
			{ ".xll", "application/x-excel" },
			{ ".xlm", "application/vnd.ms-excel" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".xlt", "application/vnd.ms-excel" },
			{ ".xlw", "application/vnd.ms-excel" }, { ".xm", "audio/x-mod" },
			{ ".xml", "text/xml" }, { ".xmz", "audio/x-mod" },
			{ ".xpi", "application/x-xpinstall" },
			{ ".xpm", "image/x-xpixmap" }, { ".xsit", "text/xml" },
			{ ".xsl", "text/xml" }, { ".xul", "text/xul" },
			{ ".xwd", "image/x-xwindowdump" }, { ".xyz", "chemical/x-pdb" },
			{ ".yz1", "application/x-yz1" },
			{ ".z", "application/x-compress" },
			{ ".zac", "application/x-zaurus-zac" },
			{ ".zip", "application/zip" }, { "", "*/*" } };
}
