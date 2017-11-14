package com.sherwin.rapid.base.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.17:30
 * @desc 标签容器控件，自动换行，但要求子控件必须高度相等
 */
public class WrapLayout extends ViewGroup {
    private int mHorizontalSpacing = 10;//水平方向间距
    private int mVerticalSpacing = 10;//垂直方向间距

    /**
     * @param context
     */
    public WrapLayout(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public WrapLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * @param context
     * @param attrs
     */
    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.mHorizontalSpacing = horizontalSpacing;
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

    public void setVerticalSpacingG(int verticalSpacing) {
        this.mVerticalSpacing = verticalSpacing;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int autualWidth = r - l - getPaddingLeft() - getPaddingRight();
//        LogUtil.e("onLayout: specWidth:" + autualWidth + "  left:" + getPaddingLeft() + "  right:" + getPaddingRight());
        int x = getPaddingLeft();// 横坐标开始
        int y = getPaddingTop();//纵坐标开始
        int rows = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            x += width + mHorizontalSpacing;
            if (x - getPaddingLeft() > autualWidth) {
                x = width + getPaddingLeft() + mHorizontalSpacing;
                rows++;
            }
            y = rows * (height + mVerticalSpacing) + getPaddingTop();
            view.layout(x - width - mHorizontalSpacing, y, x - mHorizontalSpacing, y + height);
        }
    }

    ;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int x = getPaddingLeft();//横坐标
        int y = getPaddingTop();//纵坐标
        int rows = 1;//总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
//        LogUtil.e("onMeasure: specWidth:" + specWidth + "  left:" + getPaddingLeft() + "  right:" + getPaddingRight());
        int actualWidth = specWidth;//实际宽度
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
//            child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR, PADDING_VERTICAL);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            x += width + mHorizontalSpacing;
            if (x + getPaddingRight() > actualWidth) {//换行
                x = width + getPaddingLeft() + mHorizontalSpacing;
                rows++;
            }
            y = rows * (height + mVerticalSpacing) - mVerticalSpacing + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(actualWidth, y);
    }

}