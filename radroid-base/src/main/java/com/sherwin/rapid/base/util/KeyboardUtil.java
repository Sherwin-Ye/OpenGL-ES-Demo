package com.sherwin.rapid.base.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sherwin.rapid.base.core.RapidDroid;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 软键盘显示隐藏工具
 * Created by Sherwin on 2016/6/13 18:28.
 */
public class KeyboardUtil {
    /**
     * 隐藏虚拟键盘
     *
     * @param v
     */
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 显示虚拟键盘
     *
     * @param v
     */
    public static void showKeyboard(EditText v) {
        v.setFocusable(true);
        v.requestFocus();
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) RapidDroid.application.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v,0);
    }

    /**
     * 强制显示或者关闭系统键盘
     *
     * @param txtSearchKey
     * @param status
     */
    public static void KeyBoard(final EditText txtSearchKey,
                                final boolean status) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) txtSearchKey
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (status == true) {
                    m.showSoftInput(txtSearchKey,
                            InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, 300);
    }

    /**
     * 通过定时器强制隐藏虚拟键盘
     *
     * @param v
     */
    public static void TimerHideKeyboard(final View v) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                            0);
                }
            }
        }, 10);
    }

    /**
     * 输入法是否显示着
     *
     * @param edittext
     * @return
     */
    public static boolean isShowKeyBoard(EditText edittext) {
        boolean bool = false;
        InputMethodManager imm = (InputMethodManager) edittext.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0)) {
            imm.showSoftInput(edittext, InputMethodManager.SHOW_FORCED);
            bool = true;
            // 软键盘已弹出
        } else {
            // 软键盘未弹出
        }
        return bool;
    }
}
