package com.sherwin.rapid.databinding.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sherwin.rapid.base.ui.BaseFragment;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2016/11/7.13:38
 * @desc 实现Databinding 的 Fragment
 */
public abstract class BaseDataBindingFragment<T extends ViewDataBinding> extends BaseFragment {

    public T mDataBinding;

    @Override
    public void setContentLayout(@LayoutRes int layoutId) {
        if (rootView == null) {
            mDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false);
            rootView = mDataBinding.getRoot();
            rootView.setTag(mDataBinding);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
            mDataBinding = (T) rootView.getTag();
        }
    }
}
