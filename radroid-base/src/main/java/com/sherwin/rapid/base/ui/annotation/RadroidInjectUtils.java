package com.sherwin.rapid.base.ui.annotation;

import com.sherwin.rapid.base.ui.IBaseUI;

import java.lang.reflect.Method;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/10/11.10:45
 * @desc
 */
public class RadroidInjectUtils {

    private static final String METHOD_SET_CONTENTVIEW = "setContentLayout";

    public static void inject(IBaseUI baseUI) {
        injectContentView(baseUI);
    }

    /**
     * 注入主布局文件
     *
     * @param baseUI
     */
    private static void injectContentView(IBaseUI baseUI) {
        Class<? extends IBaseUI> clazz = baseUI.getClass();
        // 查询类上是否存在ContentView注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null)// 存在
        {
            int contentViewLayoutId = contentView.layout();
            try {
                Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW,
                        int.class);
                method.setAccessible(true);
                method.invoke(baseUI, contentViewLayoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
