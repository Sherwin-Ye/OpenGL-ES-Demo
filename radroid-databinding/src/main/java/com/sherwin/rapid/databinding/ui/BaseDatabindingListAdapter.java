package com.sherwin.rapid.databinding.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.sherwin.rapid.base.ui.BaseListAdapter;

import java.util.List;

/**
 * @author Sherwin
 * @date 2016/9/13 15:25.
 * @desc 集成databinding的listadapter
 */
public abstract class BaseDatabindingListAdapter<T,K extends ViewDataBinding> extends BaseListAdapter<T> {

    public BaseDatabindingListAdapter(Context context, List<T> list){
        super(context,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        K databinding;
        T item = mList.get(position);

        if (convertView == null) {
            databinding = DataBindingUtil.inflate(inflate, getItemViewId(), null, false);
            convertView = databinding.getRoot();
            convertView.setTag(databinding);
        } else {
            databinding = (K) convertView.getTag();
        }
        bingdingView(position,item,databinding);
        return convertView;
    }

    protected abstract int getItemViewId();
    protected abstract void bingdingView(int position,T item,K databinding);
}
