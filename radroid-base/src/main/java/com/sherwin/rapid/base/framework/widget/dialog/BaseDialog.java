package com.sherwin.rapid.base.framework.widget.dialog;

import android.app.Activity;
import android.app.Dialog;

import com.sherwin.rapid.base.util.LogUtil;

/**
 * Created by Sherwin.Ye on 2016/12/5.11:06
 */
public class BaseDialog extends Dialog{
    private Activity mActivity;
    public BaseDialog(Activity context) {
        super(context);
        mActivity=context;
    }

    public BaseDialog(Activity context, int themeResId) {
        super(context, themeResId);
        mActivity=context;
    }

    protected BaseDialog(Activity context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mActivity=context;
    }

    @Override
    public void show() {
        if (!mActivity.isDestroyed()&&!mActivity.isFinishing()){
            super.show();
        }
    }

    @Override
    public void dismiss() {
        try{
            if (isShowing()){
                super.dismiss();
            }
        }catch (Exception e){
            LogUtil.e("BaseDialog",e.getMessage());
        }
    }
}
