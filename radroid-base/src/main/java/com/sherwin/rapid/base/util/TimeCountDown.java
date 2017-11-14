package com.sherwin.rapid.base.util;

import android.os.CountDownTimer;

/**
 * Created by vhaiyue on 2017/1/5.
 * 时间倒计时工具
 */

public class TimeCountDown extends CountDownTimer {

    private OnTimeCountDownListener mOnTimeCountDownListener;

    public TimeCountDown(long millisInFuture, long countDownInterval, OnTimeCountDownListener onTimeCountDownListener) {
        super(millisInFuture, countDownInterval);
        this.mOnTimeCountDownListener = onTimeCountDownListener;
    }

    @Override
    public void onFinish() {// 计时完毕
        if (mOnTimeCountDownListener!=null){
            mOnTimeCountDownListener.onFinish();
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        if (mOnTimeCountDownListener!=null){
            mOnTimeCountDownListener.onTick(millisUntilFinished);
        }
    }
    public interface OnTimeCountDownListener {
        public void onFinish();

        public void onTick(long millisUntilFinished);
    }
}