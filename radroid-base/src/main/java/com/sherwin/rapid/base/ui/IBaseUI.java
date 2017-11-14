package com.sherwin.rapid.base.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/28.10:00
 * @desc
 */
public interface IBaseUI {
    /**
     * 加载布局前执行
     */
    void beforeLoadContent();

    /**
     * 设置布局资源
     * @param layoutId
     * @return
     */
    void setContentLayout(@LayoutRes int layoutId);

    /**
     * 初始化控件信息
     */
    void initView(Bundle savedInstanceState);

    /**
     * 初始化监听
     */
    void initListener();

    /**
     * 初始化监听
     */
    void initData();
}
