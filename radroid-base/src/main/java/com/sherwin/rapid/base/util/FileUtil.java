package com.sherwin.rapid.base.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sherwin.Ye
 * @data 2015-11-26 下午3:14:40
 * @desc FileUtil.java
 */
public class FileUtil {

	private final static String TAG = "FileUtil";

	private static String configFilePath;

	/**
	 * SD卡是否存在
	 * @return
	 */
	public static boolean isSDExists() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取内置的SD卡路径
	 * @return
	 */
	public static String getInternalSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
	}

	/**
	 * 获取外置SD卡路径
	 * @return
	 */
	public static String getExtSDCardPath() {
		String sdcard_path = null;
		String sd_default = Environment.getExternalStorageDirectory().getAbsolutePath();
		if (sd_default.endsWith("/")) {
			sd_default = sd_default.substring(0, sd_default.length() - 1);
		}
		// 得到路径
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				} else if (line.contains("fuse") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				}
			}
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
		return sdcard_path;
	}

	/**
	 * 得到所有sd卡的路径
	 * @param context
	 * @return
	 */
	public static List<String> getAllSDPaths(Context context) {
		StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		try {
			String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[]{}).invoke(sm, new Object[]{});
			List<String> pathList = new ArrayList<String>();
			for (int i = 0; paths != null && i < paths.length; i++) {
				String status = (String) sm.getClass().getMethod("getVolumeState", String.class).invoke(sm, paths[i]);
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					pathList.add(paths[i]);
				}
			}
			return pathList;
		} catch (Exception e) {
			LogUtil.e(e);
			return null;
		}
	}

	/**
	 * SD卡总容量
	 * @return 单位Byte
	 */
	public static long getSDAllSize() {
		//取得SD卡文件路径  
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		//获取单个数据块的大小(Byte)  
		long blockSize = sf.getBlockSize();
		//获取所有数据块数  
		long allBlocks = sf.getBlockCount();
		//返回SD卡大小  
		return allBlocks * blockSize; //单位Byte  
		//return (allBlocks * blockSize)/1024; //单位KB  
		//		return (allBlocks * blockSize) / 1024 / 1024; //单位MB  
	}

	/**
	 * SD卡剩余空间
	 * @return 单位Byte
	 */
	public static long getSDFreeSize() {
		//取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		//获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		//空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		//返回SD卡空闲大小
		return freeBlocks * blockSize; //单位Byte
		//return (freeBlocks * blockSize)/1024;   //单位KB
		//		return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
	}

	public static boolean deleteFileByPath(String filepath) {
		boolean flag = true;
		if (StringUtil.isNotBlank(filepath)) {
			File file = new File(filepath);
			if (file.exists()) {
				flag = file.delete();
			}
		}
		return flag;
	}

	/**
	 * 文件是否存在
	 * @param filepath
	 * @return
	 */
	public static boolean isFileExists(String filepath) {
		if (StringUtil.isNotBlank(filepath)) {
			File file = new File(filepath);
			return file.exists();
		}
		return false;
	}

//	public static String getImgCache(Context context) {
//		File file = new File(context.getApplicationContext().getCacheDir(), "web_image_cache");
//		File[] fileArray = file.listFiles();
//		int size = 0;
//		if (null != fileArray) {
//			for (File f : fileArray) {
//				size += f.length();
//			}
//		}
//		return getFileSizeFormat(size);
//	}


	/**
	 * 获取文件的MD5
	 * @param filepath
	 * @return
	 * @throws Exception
     */
	public static String getMd5OfFile(String filepath) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		File f = new File(filepath);
		String output = "";
		InputStream is = new FileInputStream(f);
		byte[] buffer = new byte[8192];
		int read = 0;
		try {
			while ((read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);
			for (; output.length() < 32;) {
				output = "0" + output;
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to process file for MD5", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
			}
		}
		return output;
	}

	/**
	 * 删除文件夹和文件夹里面的文件
	 * @param pPath
     */
	public static void deleteDirWihtFile(final String pPath) {
		File dir = new File(pPath);
		deleteDirWihtFile(dir);
	}
	/**
	 * 删除文件夹和文件夹里面的文件
	 * @param dir
	 */
	public static void deleteDirWihtFile(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDirWihtFile(file); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	/**
	 * 文件重命名
	 * @param oldPath
	 * @param newPath
     */
	public static boolean rename(String oldPath,String newPath){
		if (StringUtil.isNotBlank(oldPath)&& StringUtil.isNotBlank(newPath)){
			File file = new File(oldPath);
			if (file.exists()){
				return file.renameTo(new File(newPath));
			}
		}
		return false;

	}

	/**
	 * 返回一个文件的大小 B,KB,MB,GB
	 * @param size
	 * @return
	 */
	public static String getFileSizeFormat(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		if (size < 0) {
			return "size: error";
		}
		if (size < 1024) {
			return size + "B";
		} else if (size < 1024 * 1024) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024 * 1024 * 1024) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		}
	}
}
