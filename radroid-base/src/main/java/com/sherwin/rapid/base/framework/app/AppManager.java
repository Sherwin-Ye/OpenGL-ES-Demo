package com.sherwin.rapid.base.framework.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.sherwin.rapid.base.util.LogUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @date 2017/9/27.11:26
 * @author Sherwin.Ye 674718661@qq.com
 * @Description Activity 管理类
 */
public class AppManager {
    /**
     * 存储Activity所使用的栈
     */
    private Stack<Activity> activityStack;

    private static AppManager instance = new AppManager();

    /**
     * 不允许创建
     */
    private AppManager() {
        activityStack = new Stack<>();
    }

    /**
     * 获取AppManager单例
     * @return AppManager
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (instance){
                instance = new AppManager();
            }
        }
        return instance;
    }

    /**
     * 获取activity栈
     * @return Stack<? extends Activity>
     */
    public Stack<? extends Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 获取activity实例数
     * @return int
     */
    public int getSize() {
        return activityStack.size();
    }

    /**
     * 获取栈顶部的activity
     * 当前Activity（栈中最后一个压入的）
     * @param <T extends Activity>
     * @return
     */
    public <T extends Activity> T getCurrentActivity() {
        if (activityStack.size() > 0) {
            return (T)activityStack.peek();
        }
        return null;
    }

    /**
     * 判断指定的activity是否存在
     *
     * @param activity
     * @return
     */
    public boolean exsistActivity(Activity activity) {
        // search 方法从返回 1-size，从栈顶开始计算
        return activity != null ? activityStack.search(activity) > 0 : false;
    }

    /**
     * 得到指定类名的Activity
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }


    /**
     * 创建一个Actvity时将它放入栈中
     * Activity.onCreate 中调用
     * @param activity
     */
    public void addActivity(Activity activity) {
        synchronized (activityStack){
            int index = activityStack.search(activity);
            if(index > 0){ // 如果存在，则先移除，下标从1-size
                activityStack.removeElementAt(activityStack.size() - index);
            }
            activityStack.push(activity);
        }
    }

    /**
     * 结束一个Actvity时将它从栈中移除
     * Activity.onDestroy 中调用
     * @param activity
     */
    public void removeActivity(Activity activity) {
        synchronized (activityStack){
            int index = activityStack.search(activity);
            if(index > 0){ // 如果存在，则先移除，下标从1-size
                activityStack.removeElementAt(activityStack.size() - index);
            }
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        Activity activity = getCurrentActivity();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        synchronized (activityStack){
            if (activity != null) {
                if (!activity.isFinishing()&&!activity.isDestroyed()){
                    activity.finish();
                }
                activityStack.removeElement(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        synchronized (activityStack){
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (!activity.isFinishing()&&!activity.isDestroyed()){
                    activity.finish();
                }
                iterator.remove();
            }
        }
    }

    /**
     * 结束所有Activity 除了 activityClass
     * @param activityClass 不关闭的Activity 的Class
     */
    public void finishAllActivityExcept(Class<? extends Activity> activityClass) {
        synchronized (activityStack){
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity != null && !activityClass.getCanonicalName().equals(activity.getClass().getCanonicalName())) {
                    if (!activity.isFinishing()&&!activity.isDestroyed()){
                        activity.finish();
                    }
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            LogUtil.e(e.getMessage(),e);
        }
    }

    /**
     * App 是否正在运行
     *
     * @return
     */
    public boolean isAppRunning() {
        return activityStack.size() > 0;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     * @param context
     * @return true:前台运行中   false:后台运行
     */
    public boolean isAppForeground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}