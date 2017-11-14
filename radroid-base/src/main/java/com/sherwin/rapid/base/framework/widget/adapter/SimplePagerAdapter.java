package com.sherwin.rapid.base.framework.widget.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SimplePagerAdapter extends PagerAdapter {

	private List<View> mViewList;

	public SimplePagerAdapter(List<View> viewList) {
		this.mViewList = viewList;
	}

	// 实例化
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}

	// 销毁
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

	// 获取总数
	@Override
	public int getCount() {
		return mViewList.size();
	}

	// 用于确认instantiateItem是否返回了和关键对象有关的Page视图
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
