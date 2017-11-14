package com.sherwin.rapid.base.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sherwin.radroid.base.R;
import com.sherwin.rapid.base.core.RapidDroid;


/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.16:30
 * @desc ToastUtil.java
 */
public class ToastUtil {

    public static Toast toast;
    /**
     * 显示Toast基方法
     *
     * @param text     显示的文字
     * @param duration
     */
    public static void showToast(CharSequence text, int duration) {
		Toast.makeText(RapidDroid.application, text, duration).show();
//        toast(GlobalInfo.getContext(), text, duration);
    }

    /**
     * 显示Toast基方法
     *
     * @param strResId 显示的文字资源id
     * @param duration
     */
    public static void showToast(int strResId, int duration) {
        showToast(RapidDroid.application.getResources().getString(strResId), duration);
    }

    /**
     * 短暂显示Toast方法
     *
     * @param text 显示的文字
     */
    public static void showToastShort(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast方法
     *
     * @param text 显示的文字
     */
    public static void showToastLong(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 短暂显示Toast方法
     *
     * @param strResId 显示的文字资源id
     */
    public static void showToastShort(int strResId) {
        showToast(strResId, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast方法
     *
     * @param strResId 显示的文字资源id
     */
    public static void showToastLong(int strResId) {
        showToast(strResId, Toast.LENGTH_LONG);
    }

    /**
     * 默认显示Toast方法（短暂）
     *
     * @param text 显示的文字
     */
    public static void showToast(CharSequence text) {
        showToastShort(text);
    }

    /**
     * 默认显示Toast方法（短暂）
     *
     * @param strResId 显示的文字资源id
     */
    public static void showToast(int strResId) {
        showToastShort(strResId);
    }

    /**
     * 自定义Toast 样式
     *
     * @param context
     * @param msg
     * @param time
     */
    public static void toast(Context context, CharSequence msg, int time) {
        if (toast==null){
            toast = Toast.makeText(context, null, time);
        }else{
            toast.setDuration(time);
        }
        LinearLayout layout = (LinearLayout) toast.getView();
        /*layout.setLayoutParams(new WindowManager.LayoutParams(10000,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT));*/
        layout.setBackgroundResource(R.drawable.toast_radius_shape);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin=DisplayUtil.dp2px(context,20);
        params.setMargins(margin, 10, margin, 10);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setLineSpacing(5,1.3f);
        tv.setTextColor(context.getResources().getColor(R.color.toast_text_transparent));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setPadding(0, 0, 0, 0);
        tv.setText(msg);
        layout.removeAllViews();
        layout.addView(tv);
        toast.setGravity(Gravity.CENTER, 0, 0);//居中显示
        toast.show();
    }
}
