package com.sherwin.rapid.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Sherwin.Ye
 * @date 2016/9/19 14:00.
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 */
public class SPTool {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "shared_preferences_data";

    private SharedPreferences mSharedPreferences;


    /**
     * 对象方式
     * @param context
     * @return
     */
    public SPTool create(Context context){
        mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return this;
    }

    /**
     * 删除
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        if(object == null){
            editor.remove(key);
            editor.apply();
            return;
        }

        String type = object.getClass().getSimpleName();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        //apply与commit作用相同，虽没返回值，但效率更高
        editor.apply();
    }

    public int getParamToInt(String key) {
        return getParamToInt(key, 0);
    }
    public int getParamToInt(String key, int defaultObject) {
        return mSharedPreferences.getInt(key, defaultObject);
    }
    public long getParamToLong(String key) {
        return getParamToLong(key, 0);
    }
    public long getParamToLong(String key, long defaultObject) {
        return mSharedPreferences.getLong(key, defaultObject);
    }
    public boolean getParamToBoolean(String key) {
        return getParamToBoolean(key, false);
    }
    public boolean getParamToBoolean(String key, boolean defaultObject) {
        return mSharedPreferences.getBoolean(key, defaultObject);
    }
    public float getParamToFloat(String key) {
        return getParamToFloat(key, 0);
    }
    public float getParamToFloat(String key, float defaultObject) {
        return mSharedPreferences.getFloat(key, defaultObject);
    }
    public String getParamToString( String key) {
        return getParamToString(key, null);
    }
    public String getParamToString( String key, String defaultObject) {
        return mSharedPreferences.getString(key, defaultObject);
    }


// =====================以下是静态方法=======================================
    /**
     * 删除
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(object == null){
            editor.remove(key);
            editor.apply();
            return;
        }

        String type = object.getClass().getSimpleName();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        //apply与commit作用相同，虽没返回值，但效率更高
        editor.apply();
    }

    public static int getParamToInt(Context context, String key, int defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultObject);
    }

    public static long getParamToLong(Context context, String key, long defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultObject);
    }

    public static boolean getParamToBoolean(Context context, String key, boolean defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultObject);
    }

    public static float getParamToFloat(Context context, String key, float defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultObject);
    }

    public static String getParamToString(Context context, String key, String defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultObject);
    }
}
