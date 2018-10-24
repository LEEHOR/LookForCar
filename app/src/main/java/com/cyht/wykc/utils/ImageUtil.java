package com.cyht.wykc.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class ImageUtil {

//	public static final long MAX_BITMAP_SIZE = 1024 * 1024 * 4; // 允许的图片最大值;
//	public static final long SAFE_DISTANCE = 1024 * 100; // 至少要保留多大预留空间;

//	public static final long SCALE_BOUND_SIZE = 1024; // 图片宽高最小值大于这个时，选择缩小图片而不是画质.

	public static final int ROTATE_MAX_SIZE = 512; // 旋转图片时，图片缩放的最大尺寸

//	public static final int UNCONSTRAINED = -1;

	/**
	 * 进行图片缩放
	 */
	public static Bitmap scaleBitmapTo(Bitmap bitmap, int size) {
		if (bitmap == null) {
			return null;
		}
		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();
		int dstWidth = size;
		int dstHeight = size;
		if (srcWidth > srcHeight) {
			dstHeight = ((size * srcHeight) / srcWidth);
		} else {
			dstWidth = ((size * srcWidth) / srcHeight);
		}
//		Bitmap dstBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth,dstHeight, true);
		return Bitmap.createScaledBitmap(bitmap, dstWidth,dstHeight, true);
	}

	/**
	 * 图片缩放到指定宽高
	 * <p/>
	 * 非等比例压缩，图片会被拉伸
	 *
	 * @param bitmap 源位图对象
	 * @param w      要缩放的宽度
	 * @param h      要缩放的高度
	 * @return 新Bitmap对象
	 */
	public static Bitmap scaleBitmapTo(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
	}
	
	public static Bitmap scaleImageFromFile(File imageFile, int size) {
		if (imageFile == null || !imageFile.isFile() || !imageFile.exists()
				|| size <= 0) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		// 获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
	}

	public static Bitmap scaleImageFromInputStream(InputStream stream, int size) {
		if (stream == null || size <= 0) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeStream(stream, null, options);
		// 获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;
		// 根据options参数，减少所需要的内存
		return BitmapFactory.decodeStream(stream, null, options);
	}

	/**
	 * 图片等比例压缩
	 */
	public static Bitmap scaleImageFromAbsolutePath(String filePath, int reqWidth,
                                                    int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static Bitmap scaleImageFromAbsolutePath(String imageFile, int size) {
		if (TextUtils.isEmpty(imageFile)) return null;
		File file = new File(imageFile);
		if (!file.isFile() || !file.exists() || size <= 0) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFile(imageFile, options);
		// 获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;
		// 根据options参数，减少所需要的内存
		return BitmapFactory.decodeFile(imageFile, options);
	}

	/*
	 * 进行图片缩放
	 */
	public static Bitmap scaleImageFromResource(Resources res, int resourse,
                                                int size) {
		if (size <= 0) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeResource(res, resourse, options);
		// 获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		return BitmapFactory.decodeResource(res, resourse, options);
	}

	/**
	 * 将bitmap压缩成file
	 */
	 public static void compressBitmapToFile(Bitmap bmp, File file){
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        int options = 80;//个人喜欢从80开始,
	        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
	        while (baos.toByteArray().length / 1024 > 100) {
	            baos.reset();
	            options -= 10;
	            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
	        }
	        try {
	        	if (file.exists()) {
					file.delete();
					file.createNewFile();
				}
	            FileOutputStream fos = new FileOutputStream(file);
	            fos.write(baos.toByteArray());
	            fos.flush();
	            fos.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	/**
	 * 复制图片并进行缩放
	 */
	public static boolean copyImageFileToScaleFile(File source, File dest, int size) {
		boolean isSuccess = false;
		if (source == null || !source.isFile() || !source.exists()
				|| dest == null || size <= 0) {
			return isSuccess;
		}
		Bitmap bitmap = scaleImageFromFile(source, size);
		if (bitmap == null) {
			return isSuccess;
		}
		FileOutputStream fos = null;
		try {
			if (!dest.exists()) {
				dest.createNewFile();
			}
			fos = new FileOutputStream(dest);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			isSuccess = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bitmap.recycle();
		return isSuccess;
	}

	/**
	 * 进行图片缩放
	 */
	public static Bitmap scaleImageFromUri(ContentResolver resolver, Uri uri,
                                           int size) {
		if (resolver == null || uri == null || size <= 0) {
			return null;
		}
		ParcelFileDescriptor pfd;
		try {
			pfd = resolver.openFileDescriptor(uri, "r");
		} catch (IOException ex) {
			return null;
		}
		java.io.FileDescriptor fd = pfd.getFileDescriptor();
		BitmapFactory.Options options = new BitmapFactory.Options();

		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		// 获得缩放比例
		options.inSampleSize = getScaleSampleSize(options, size);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	/**
	 * 这个函数会对图片的大小进行判断，并得到合适的缩放比例，比如2即1/2,3即1/3
	 * 
	 * @target: 要缩放成的宽或高
	 * 
	 * @return: 缩放比例
	 */
	public static int getScaleSampleSize(BitmapFactory.Options options,
			int target) {
		if (options == null || target <= 0) {
			return 1;
		}

		int w = options.outWidth;
		int h = options.outHeight;
		int candidateW = w / target;
		int candidateH = h / target;
		int candidate = Math.max(candidateW, candidateH);
		if (candidate == 0)
			return 1;
		if (candidate > 1) {
			if ((w > target) && (w / candidate) < target) {
				candidate -= 1;
			}
		}
		if (candidate > 1) {
			if ((h > target) && (h / candidate) < target) {
				candidate -= 1;
			}
		}
		return candidate;
	}

	/**
	 * 计算InSampleSize
	 * 宽的压缩比和高的压缩比的较小值  取接近的2的次幂的值
	 * 比如宽的压缩比是3 高的压缩比是5 取较小值3  而InSampleSize必须是2的次幂，取接近的2的次幂4
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			int ratio = heightRatio < widthRatio ? heightRatio : widthRatio;
			// inSampleSize只能是2的次幂  将ratio就近取2的次幂的值
			if (ratio < 3)
				inSampleSize = ratio;
			else if (ratio < 6.5)
				inSampleSize = 4;
			else if (ratio < 8)
				inSampleSize = 8;
			else
				inSampleSize = ratio;
		}

		return inSampleSize;
	}

	/**
	 * 获得圆角图片
	 */
	public static Bitmap createRoundedBitmap(String path, int size, int radius) {

		Bitmap bitmap = null;
		try {
			bitmap = ImageUtil.scaleImageFromAbsolutePath(path, size / 4);
			bitmap = ImageUtil.createRoundedCornerBitmap(bitmap, radius);
		} catch (Exception e) {
		}
		return bitmap;
	}

	/**
	 * 带圆角的图像
	 * */
	public static Bitmap createRoundedCornerBitmap(Bitmap src, int radius) {
		final int w = src.getWidth();
		final int h = src.getHeight();
		// 高质量32位图
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bitmap);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(0xff424242);
		// 防止边缘的锯齿
		paint.setAntiAlias(true);
		// 用来对位图进行滤波处理
		paint.setFilterBitmap(true);
		Rect rect = new Rect(0, 0, w, h);
		RectF rectf = new RectF(rect);
		// 绘制带圆角的矩形
		canvas.drawRoundRect(rectf, radius, radius, paint);

		// 取两层绘制交集。显示上层
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制图像
		canvas.drawBitmap(src, rect, rect, paint);
		return bitmap;
	}
	
	/**
	 *保存图片到sd卡
	 */
	public static void saveBitmapToSDCard(Bitmap photoBitmap, String path,
                                          String photoName) {
		if (checkSDCardAvailable()) {
			File photoFile = new File(path, photoName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void saveBitmapToSDCard(Bitmap photoBitmap, String path) {
		if (checkSDCardAvailable()) {
			File photoFile = new File(path);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 检查是否载有sd卡
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 根据路径加载bitmap
	 * 
	 * @param path
	 *            路径
	 * @param w
	 *            款
	 * @param h
	 *            长
	 * @return
	 */
	public static Bitmap convertAbsoultePathToBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 设置为ture只获取图片大小
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Config.ARGB_8888;
			// 返回为空
			BitmapFactory.decodeFile(path, opts);
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				// 缩放
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int) scale;
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
					BitmapFactory.decodeFile(path, opts));
			Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak
					.get().getWidth(), weak.get().getHeight(), null, true);
			if (bMapRotate != null) {
				return bMapRotate;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算图片在内存中，占的大小 2 是像素的深度，一般占用两个字节
	 */
	public static long calculateBitmapSize(BitmapFactory.Options options) {
		int depth = 2;
		if (options.inPreferredConfig == null) {
			depth = 2;
		} else {
			switch (options.inPreferredConfig) {
			case ARGB_4444:
				depth = 2;
				break;
			case ARGB_8888:
				depth = 4;
				break;
			default:
				depth = 2;
				break;
			}
		}
		return options.outWidth * options.outHeight * depth; // options.outWidth
																// *
																// options.outHeight
																// * depth /
																// sampleSize
	}

	/**
	 * 查询图片旋转角度
	 */
	public static int getExifOrientation(String filepath) {// YOUR MEDIA PATH AS STRING
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;
				}

			}
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param bitmap
	 *            图片
	 * @param degrees
	 *            角度
	 * @return 旋转后的图片Bitmap对象
	 */
	public static Bitmap rotate(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix matrix = new Matrix();
			matrix.setRotate(degrees, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2);
			try {
				Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				if (bitmap != rotated) {
					bitmap.recycle();
					bitmap = rotated;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return bitmap;
	}

	/**
	 * 旋转图片文件
	 * 
	 * @param imageFile
	 *            图片文件
	 * @param degrees
	 *            角度
	 * @return 缩放并旋转后的图片文件
	 */
	public static boolean rotateImageFromFile(File imageFile, int degrees) {
		boolean isSuccess = false;
		if (imageFile == null || !imageFile.isFile() || !imageFile.exists()) {
			return isSuccess;
		}
		Bitmap bitmap = scaleImageFromFile(imageFile, ROTATE_MAX_SIZE);
		Bitmap rotated = rotate(bitmap, degrees); // 翻转图片
		FileOutputStream fileOutputStream = null;
		try {
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}
			fileOutputStream = new FileOutputStream(imageFile);
			rotated.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
			isSuccess = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bitmap.recycle();
		rotated.recycle();
		return isSuccess;
	}

	public static Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}

	public static Bitmap getBitmap(String path) {
		try {
			InputStream in = new FileInputStream(path);
			return BitmapFactory.decodeStream(in);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static BitmapFactory.Options getBitmapOptions(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// if (StringUtil.isEmpty(path)) {
		// return options;
		// }
		File imageFile = new File(path);
		if (imageFile == null || !imageFile.isFile() || !imageFile.exists()) {
			return options;
		}
		options = new BitmapFactory.Options();
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		return options;
	}

}
