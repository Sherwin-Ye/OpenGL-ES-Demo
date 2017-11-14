package com.sherwin.rapid.base.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Sherwin
 * @date 2016/9/13 15:25.
 * @desc 基础AdapterView
 */
public abstract class BaseListAdapter<T> extends BaseAdapter{
    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater inflate;

    public BaseListAdapter(Context context, List<T> list){
        this.mContext=context;
        this.mList=list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public T getItem(int position) {
        return (mList==null||mList.size()<=position)?null:mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void startActivity(Class activityClass){
        Intent intent=new Intent(mContext,activityClass);
        mContext.startActivity(intent);
    }
}
