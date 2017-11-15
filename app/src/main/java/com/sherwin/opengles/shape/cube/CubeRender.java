package com.sherwin.opengles.shape.cube;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import com.sherwin.opengles.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/14.9:07
 * @desc
 */
public class CubeRender implements GLSurfaceView.Renderer {
    private Context context;

    public CubeRender(Context context){
        this.context = context;
    }

    //定义三角形对象
    Cube cube;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //First:设置清空屏幕用的颜色，前三个参数对应红绿蓝，最后一个对应alpha
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        cube = new Cube(context);
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //Second:设置视口尺寸，即告诉opengl可以用来渲染的surface大小
        glViewport(0,0,width,height);

        float ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
        MatrixState.setProjectFrustum(-ratio,ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixState.setCamera(-16f, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        //Third:清空屏幕，擦除屏幕上所有的颜色，并用之前glClearColor定义的颜色填充整个屏幕
        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        //绘制图形
        cube.draw();
    }
}
