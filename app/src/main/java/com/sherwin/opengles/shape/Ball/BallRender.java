package com.sherwin.opengles.shape.Ball;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sherwin.opengles.shape.circle.Circle;
import com.sherwin.opengles.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/14.9:07
 * @desc
 */
public class BallRender implements GLSurfaceView.Renderer {
    private Context context;

    public BallRender(Context context){
        this.context = context;
    }

    //定义三角形对象
    Ball ball;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.w("MyRender","onSurfaceCreated");
        // TODO Auto-generated method stub
        //First:设置清空屏幕用的颜色，前三个参数对应红绿蓝，最后一个对应alpha
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        ball = new Ball(context);
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0,0,width,height);
        float ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
        MatrixState.setProjectFrustum(-ratio,ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixState.setCamera(0, 0, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        Log.w("MyRender","onDrawFrame");
        // TODO Auto-generated method stub
        //Third:清空屏幕，擦除屏幕上所有的颜色，并用之前glClearColor定义的颜色填充整个屏幕
        glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        //绘制三角形
        ball.draw();
    }
}
