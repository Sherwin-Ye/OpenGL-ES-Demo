package com.sherwin.rapid.base.framework.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * @author Sherwin.Ye
 * @data 2016-12-3 下午3:40:04
 * @desc SimpleFragmentPagerAdapter.java
 */
public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private List<? extends Fragment> mFragmentList;

	public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, List<? extends Fragment> fragmentList) {
		super(fragmentManager);
		this.mFragmentList = fragmentList;
	}


	@Override
	public Fragment getItem(int position) {
		if (mFragmentList==null||mFragmentList.size()<=position){
			return null;
		}
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	public List<? extends Fragment> getFragmentList() {
		return mFragmentList;
	}
	@Override
	public int getItemPosition(Object object) {
		//这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
		// 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
		return PagerAdapter.POSITION_NONE;
	}
}
