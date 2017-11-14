package com.sherwin.rapid.base.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.17:20
 * @desc 带滚动监听的SrcllView
 */
public class ObservableScrollView extends ScrollView {
    private OnScrollListener mOnScrollListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    /**
     * 得到可滑动的最大高度
     * @return
     */
    public int getTotalScrollHeight(){
        View child = getChildAt(0);
        int height = child.getMeasuredHeight();
        height = height - getMeasuredHeight();
        return  height;
    }
    public interface OnScrollListener {

        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
