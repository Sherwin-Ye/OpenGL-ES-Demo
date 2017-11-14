package com.sherwin.rapid.base.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;


public class PackageUtil {


    private static final String TAG = "PackageUtil";
    private static PkgInfo pkgInfo;
    /**
     * @author Sherwin.Ye
     * @data 2016年9月14日 15:29:51
     * @desc PkgInfo.java
     */
    public static class PkgInfo {

        private Integer versionCode;// 版本号
        private String versionName;// 版本名称
        private String packageName;// 包名

        public Integer getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(Integer versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

    }


    /**
     * 初始化pkginfo
     *
     * @param context
     */
    private static void initInfo(Context context) {
        pkgInfo = new PkgInfo();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            pkgInfo.setVersionCode(packageInfo.versionCode);
            pkgInfo.setVersionName(packageInfo.versionName);
            pkgInfo.setPackageName(packageInfo.packageName);
        } catch (Exception e) {
            LogUtil.e("getPackageInfo error : " + e);
        }
    }


    /**
     * 得到应用信息
     *
     * @param context
     * @return
     */
    public static PkgInfo getInfo(Context context) {
        if (null == pkgInfo) {
            initInfo(context);
        }
        return pkgInfo;
    }

    public static int getVersionCode(Context context) {
        return getInfo(context).getVersionCode();
    }

    public static String getVersionName(Context context) {
        return getInfo(context).getVersionName();
    }

    /**
     * 获取MetaData
     *
     * @param context
     * @param keyName
     * @return
     */
    public static String getMetaData(Context context, String keyName) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            Object obj = bundle.get(keyName);
            if (null == obj) {
                return null;
            } else {
                String data = String.valueOf(obj);
                return data;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断安装包是否已将安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        return PackageUtil.getPackageInfo(context, packagename) != null;
    }

    /**
     * 判断已安装的应用是不是最新版本
     *
     * @param context
     * @param packagename
     * @param latestVersionCode
     * @return
     */
    public static boolean isAppLatest(Context context, String packagename, int latestVersionCode) {
        PackageInfo info = getPackageInfo(context, packagename);
        if (info != null) {
            return info.versionCode >= latestVersionCode;
        }
        return false;
    }


    /**
     * 根据获取PackageInfo
     *
     * @param context
     * @param packagename
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String packagename) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packagename, 0);
            return info;
        } catch (Exception e) {
            LogUtil.w(TAG, "getPackageInfo by pkg error : " + e);
            return null;
        }
    }

    /**
     * 获取指定的package versioncode
     *
     * @param context
     * @param packagename
     * @return
     */
    public static Integer getVersionCode(Context context, String packagename) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packagename, 0);
            return info.versionCode;
        } catch (Exception e) {
            LogUtil.w(TAG, "getVersionCode by pkg error : " + e);
            return null;
        }
    }

    /**
     * 安装应用
     * @param context
     * @param apkPath
     */
    public static void installApk(Context context,String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
