package com.sherwin.rapid.base.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sherwin.radroid.base.R;

/**
 * 电量百分比的view
 *
 * @author Sherwin.Ye
 * @data 2016年5月5日 下午2:10:30
 * @desc ElectricityView.java
 */
public class ElectricityViewOld extends TextView {

    private static final int COLOR_DEFAULT = Color.rgb(39, 39, 39);

    /**
     * 电量百分比 0.0~1.0
     */
    private float power = 0f;
    /**
     * 角半径
     */
    private int radio = 0;

    /**
     * 边框宽度
     */
    private int border = 0;

    private Paint mPaint;
    /**
     * 标签背景颜色，包括填充色
     */
    private int mainColor = COLOR_DEFAULT;
    /**
     * 标签内部背景颜色，如果是填充时无效
     */
    private int bgColor = Color.WHITE;

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    /**
     * 得到label背景颜色
     *
     * @return
     */
    public int getMainColor() {
        return mainColor;
    }

    /**
     * 设置label背景颜色
     *
     * @param mainColor
     */
    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        invalidate();
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
        invalidate();
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
        invalidate();
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
        invalidate();
    }

    public ElectricityViewOld(Context context) {
        this(context, null);
    }

    public ElectricityViewOld(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElectricityViewOld(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ElectricityView, defStyle, 0);
        border = typedArray.getDimensionPixelSize(R.styleable.ElectricityView_electric_border_width, dp2px(getContext(), 1));
        radio = typedArray.getDimensionPixelSize(R.styleable.ElectricityView_electric_radius, dp2px(getContext(), 2));
        bgColor = typedArray.getColor(R.styleable.ElectricityView_electric_bg_color, Color.WHITE);
        mainColor = typedArray.getColor(R.styleable.ElectricityView_electric_main_color, COLOR_DEFAULT);
        power = typedArray.getFloat(R.styleable.ElectricityView_electric_power, 0.0f);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getTextSize());
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public static int dp2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //		super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int bodyWidth = width - border;
        int powerWidth = width - border * 5;
        int powerHeight = height - border * 4;
        RectF ovalf = new RectF(0, 0, bodyWidth, height);// 设置个新的长方形
        mPaint.setColor(mainColor);
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        //		if (!isFilled) {
        //			//  绘制一个多边形  矩形边框
        //			mPaint.setStyle(Paint.Style.STROKE);//设置空心
        //		} else {
        //			//  绘制一个多边形  矩形填充
        //			mPaint.setStyle(Paint.Style.FILL);
        //		}
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(ovalf, radio, radio, mPaint);//第二个参数是x半径，第三个参数是y半径

        RectF ovalf2 = new RectF(border, border, bodyWidth - border, height - border);// 设置个新的长方形
        mPaint.setColor(bgColor);
        float radioIn = radio - 2f;
        if (radioIn < 0) {
            radioIn = 0;
        }
        canvas.drawRoundRect(ovalf2, radioIn, radioIn, mPaint);//第二个参数是x半径，第三个参数是y半径

        mPaint.setColor(mainColor);

        RectF rightRect = new RectF(bodyWidth, height * 1 / 3, width, height * 2 / 3);// 设置个新的长方形
        canvas.drawRect(rightRect, mPaint);
        if (power < 0) {
            power = 0;
        } else if (power > 1) {
            power = 1;
        }
        RectF powerRect = new RectF(border + border, border + border, border + border + powerWidth * power, border + border + powerHeight);// 设置个新的长方形
        canvas.drawRect(powerRect, mPaint);

        super.onDraw(canvas);
    }

}
