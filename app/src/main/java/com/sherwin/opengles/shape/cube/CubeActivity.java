package com.sherwin.opengles.shape.cube;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.sherwin.opengles.R;
import com.sherwin.opengles.databinding.CubeActivityBinding;
import com.sherwin.opengles.databinding.FirstActivityBinding;
import com.sherwin.opengles.utils.MatrixState;
import com.sherwin.rapid.base.ui.annotation.ContentView;
import com.sherwin.rapid.base.util.DisplayUtil;
import com.sherwin.rapid.databinding.ui.BaseDataBindingActivity;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/14.16:44
 * @desc 绘制矩形
 */
@ContentView(layout = R.layout.cube_activity)
public class CubeActivity extends BaseDataBindingActivity<CubeActivityBinding> {

    @Override
    public void initView(Bundle savedInstanceState) {

        /* 以下是重点 */
        GLSurfaceView demoGlv = mDataBinding.glvDemo;
        // 设置OpenGL版本(一定要设置)
        demoGlv.setEGLContextClientVersion(2);
        // 设置渲染器(后面会着重讲这个渲染器的类)
        demoGlv.setRenderer(new CubeRender(this));
        // 设置渲染模式为连续模式(会以60fps的速度刷新)
        demoGlv.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        /* 重点结束 */

    }

    @Override
    public void initListener() {

        mDataBinding.glvDemo.setOnTouchListener(new View.OnTouchListener() {
            private float mPreviousX;//上次的触控位置X坐标
            private float mPreviousY;//上次的触控位置Y坐标

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();//当前的触控位置X坐标
                float y = event.getY();//当前的触控位置X坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE://检测到移动事件时
                        float dx = x - mPreviousX;
                        float dy = mPreviousY - y;
                        int width = DisplayUtil.getScreenWidth(mActivity);
                        int height = DisplayUtil.getScreenHeight(mActivity);
                        MatrixState.translate(dx*2 / width, dy*2 / height, 0);
                }
                mPreviousX = x;
                mPreviousY = y;
                return true;
            }
        });
        mDataBinding.btnRadioH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrixState.rotate(30, 0, 1, 0);
            }
        });
        mDataBinding.btnRadioV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrixState.rotate(30, 1, 0, 0);
            }
        });
        mDataBinding.btnScaleSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrixState.scale(0.8f, 0.8f, 0.8f);
            }
        });
        mDataBinding.btnScaleBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrixState.scale(1.25f, 1.25f, 1.25f);
            }
        });
    }
}
