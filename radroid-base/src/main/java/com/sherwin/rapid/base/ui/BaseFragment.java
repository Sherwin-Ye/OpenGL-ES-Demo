package com.sherwin.rapid.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sherwin.rapid.base.ui.annotation.RadroidInjectUtils;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2016/11/7.13:38
 * @desc
 */
public abstract class BaseFragment extends Fragment implements IBaseUI {
    protected View rootView;
    protected LayoutInflater layoutInflater;
    protected Activity mActivity;

    public BaseFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = inflater;
        beforeLoadContent();
        RadroidInjectUtils.inject(this);
        return rootView;
    }

    @Override
    public void setContentLayout(@LayoutRes int layoutId) {
        if (rootView == null) {
            rootView = layoutInflater.inflate(layoutId, null);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        initListener();
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }


    /**
     * 快速启动一个activity
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    /**
     * 关闭当前activity
     */
    protected void finish() {
        mActivity.finish();
    }

}
