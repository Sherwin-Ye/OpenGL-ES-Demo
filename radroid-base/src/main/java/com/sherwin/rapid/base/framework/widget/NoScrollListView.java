/**
 * @author yxw
 * date : 2013年12月10日 上午10:07:25 
 */
package com.sherwin.rapid.base.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.17:19
 * @desc 不带滚动ListView
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}