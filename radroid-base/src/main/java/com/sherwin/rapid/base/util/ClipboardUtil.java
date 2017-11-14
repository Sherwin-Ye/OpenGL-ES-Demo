package com.sherwin.rapid.base.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.16:38
 * @desc 剪切板功能
 */
public class ClipboardUtil {
    /**
     * 实现文本复制功能
     * add by Sherwin.Ye
     * @param content
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void copy(String content, Context context)
    {
        // 得到剪贴板管理器
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            android.content.ClipboardManager cmb = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
        }else{
            android.text.ClipboardManager cmb = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
        }

    }
    /**
     * 实现粘贴功能
     * add by Sherwin.Ye
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static String paste(Context context)
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            android.content.ClipboardManager cmb = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            return cmb.getText().toString().trim();
        }else{
            android.text.ClipboardManager cmb = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            return cmb.getText().toString().trim();
        }
    }
}
