package com.sherwin.rapid.base.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕尺寸和尺寸单位换算
 * @author Sherwin.Ye
 * @data 2016年3月18日 上午10:32:21
 * @desc DisplayUtil.java
 */
public class DisplayUtil {

    /**
     * 获取 DisplayMetrics
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        try {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        try {
            float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 得到屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }
    /**
     * 得到屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 得到屏幕像素密度
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }
}
