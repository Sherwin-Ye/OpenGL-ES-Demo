package com.sherwin.rapid.base.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sherwin.rapid.base.framework.app.AppManager;
import com.sherwin.rapid.base.plugs.permission.PermissionGen;
import com.sherwin.rapid.base.ui.annotation.RadroidInjectUtils;


/**
 * @author Sherwin.Ye
 * @data 2016年9月13日 17:53:01
 * @desc BaseActivity.java
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback,IBaseUI {

    protected Activity mActivity;

    /**
     * 设置状态栏颜色
     *
     * @param statusColor
     */

    public void setStatusBarColor(int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarBgColor(statusColor);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarBgColor(int statusColor) {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setWindowBgColor(int color) {
        ViewGroup rootViewGroup = getRootViewGroup();
        rootViewGroup.setBackgroundColor(color);
    }

    /**
     * 得到根节点的Group
     *
     * @return
     */
    public ViewGroup getRootViewGroup() {
        return ((ViewGroup) findViewById(android.R.id.content));
    }

    /**
     * 得到根节点
     *
     * @return
     */
    public View getRootView() {
        ViewGroup rootViewGroup = getRootViewGroup();
        if (rootViewGroup.getChildCount() > 0) {
            return rootViewGroup.getChildAt(0);
        } else {
            return null;
        }
    }

    /**
     * 加载布局前执行
     */
    @Override
    public void beforeLoadContent() {

    }

    /**
     * 初始化控件信息
     */

    /**
     * 初始化监听
     */
    public void initListener() {

    }

    /**
     * 初始化监听
     */
    public void initData() {

    }

    /**
     * 默认使用默认的方式设置布局
     */
    @Override
    public void setContentLayout(@LayoutRes int layoutId){
        setContentView(layoutId);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = this;
        // 创建Activity 并压入栈顶
        AppManager.getAppManager().addActivity(this);
        beforeLoadContent();
        RadroidInjectUtils.inject(this);// 设置布局
        initView(bundle);
        initListener();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity 并从栈中移除
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 快速启动一个activity
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 快速启动一个activity
     *
     * @param clazz
     * @param requestCode
     */
    protected void startActivityForResult(Class clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 是否必须登录
     *
     * @return
     */
    public boolean isNeedLogin() {
        return false;
    }

    /**
     * 关闭当前页，提供给view中使用
     *
     * @param view
     */
    public void goBack(View view) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
