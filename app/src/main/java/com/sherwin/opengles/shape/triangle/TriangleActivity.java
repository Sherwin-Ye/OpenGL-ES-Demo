package com.sherwin.opengles.shape.triangle;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.sherwin.opengles.R;
import com.sherwin.opengles.databinding.FirstActivityBinding;
import com.sherwin.rapid.base.ui.annotation.ContentView;
import com.sherwin.rapid.databinding.ui.BaseDataBindingActivity;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/14.16:44
 * @desc 绘制三角形
 */
@ContentView(layout = R.layout.first_activity)
public class TriangleActivity extends BaseDataBindingActivity<FirstActivityBinding> {

    @Override
    public void initView(Bundle savedInstanceState) {

        /* 以下是重点 */
        GLSurfaceView demoGlv = (GLSurfaceView) findViewById(R.id.glv_demo);
        // 设置OpenGL版本(一定要设置)
        demoGlv.setEGLContextClientVersion(2);
        // 设置渲染器(后面会着重讲这个渲染器的类)
        demoGlv.setRenderer(new TriangleRender(this));
        // 设置渲染模式为连续模式(会以60fps的速度刷新)
        demoGlv.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        /* 重点结束 */
    }

}
