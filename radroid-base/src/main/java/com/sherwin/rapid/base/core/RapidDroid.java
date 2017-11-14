package com.sherwin.rapid.base.core;

import android.app.Application;

import com.sherwin.rapid.base.util.LogUtil;


/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/10/9.13:57
 * @desc 框架核心类
 */
public class RapidDroid {
    public static Application application;
    public static boolean debug = false;
    public static int logLevel = LogUtil.LEVEL_DEFAULT;

    /**
     * 初始化框架
     * @param application
     * @param isDebug
     */
    public static void init(Application application,boolean isDebug){
        RapidDroid.application = application;
        RapidDroid.debug = isDebug;
        initConfOfLog();
    }
    /**
     * 初始化日志等级
     */
    private static void initConfOfLog() {
        if (debug) {
            logLevel = LogUtil.DEBUG;
        } else {
            logLevel = LogUtil.LEVEL_DEFAULT;
        }
    }
}
