package com.sherwin.opengles.shape.circle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.sherwin.opengles.R;
import com.sherwin.opengles.utils.ShaderHelper;
import com.sherwin.opengles.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/15.9:48
 * @desc 定义一个三角形的参数
 */
public class Circle {
    private Context context;

    // float占4个字节
    private static final int BYTES_PER_FLOAT = 4;
    // 数组中每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 2;


    //定义两个标签，分别于着色器代码中的变量名相同,
    //第一个是顶点着色器的变量名，第二个是片段着色器的变量名
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";

    //定义两个ID,我们就是通ID来实现数据的传递的,这个与前面
    //获得program的ID的含义类似的
    private int uColorLocation;
    private int aPositionLocation;

    // 着色器 program的id
    private int program;


    // openGL 使用的float数据类型
    private FloatBuffer vertexBuffer;


    // 定义圆心坐标
    private float x;
    private float y;
    // 半径
    private float r;
    // 三角形分割的数量
    private int count = 100;



    /*
     * 第一步: 定义投影矩阵相关
     */
    private static final String U_MATRIX = "u_Matrix";
    private final float[] projectionMatrix = new float[16];
    private int uMatrixLocation;


    public Circle(Context context){
        this.context = context;
        x = 0f;
        y = 0f;
        r = 0.6f;
        initVertexData();

    }

    private void initVertexData(){
        // 顶点的个数，我们分割count个三角形，有count+1个点，再加上圆心共有count+2个点
        final int nodeCount = count + 2;
        // 定义一个数组，存放顶点
        float circleCoords[] = new float[nodeCount * COORDS_PER_VERTEX];


        // x y
        int offset = 0;
        // 存放中心点
        circleCoords[offset++] = x;
        circleCoords[offset++] = y;
        // 存放每个边缘顶点
        for (int i = 0; i < count + 1; i++) {
            float angleInRadians = ((float) i / (float) count)
                    * ((float) Math.PI * 2f);
            circleCoords[offset++] = x + r * (float)Math.sin(angleInRadians);
            circleCoords[offset++] = y + r * (float)Math.cos(angleInRadians);
        }


        // 初始化 FloatBuffer 的参数,float占4个字节
        vertexBuffer = ByteBuffer
                .allocateDirect(circleCoords.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(circleCoords);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);
        getProgram();


        //获取这两个ID ，是通过前面定义的标签获得的
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

        /*
         * 第二步: 获取顶点着色器中投影矩阵的location
         */
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    //获取program的id
    private void getProgram(){
        //获取顶点着色器文本
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.circle_vertex_shader);
        //获取片段着色器文本
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);
        //获取program的id
        program = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(program);
    }

    /**
     * 第三步 ： 根据屏幕的width 和 height 创建投影矩阵
     * @param width
     * @param height
     */
    public void projectionMatrix(int width,int height){
        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if(width > height){
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }else{
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    //绘制
    public void draw(){
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        /*
         * 第四步：传入投影矩阵
         */
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, count + 2);
//        GL_TRIANGGLES ：将传入的顶点按照没3个一组组成一个三角形进行绘制
//
//        GL_TRIANGLE_TRIP：将传入的顶点按照顺序三个一组组成三角形进行，前面三个顶点的后两个顶点做为下一个三角形的前两个顶点，
//        比如 有v0 v1 v2 v3 四个顶点顺序排列，则v0 v1 v2组成一个三角形，v1,v2,v3组成一个三角形。
//
//        GL_TRIANGLE_FAN：三角形扇的形式，将传入的顶点数据的第一个顶点做为中心点，其他点做为边缘点绘制一系列组成扇形的相邻三角形。

    }
}
