package com.sherwin.opengles.shape.triangle;

import android.content.Context;
import android.opengl.GLES20;

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
public class Triangle {
    private Context context;
    // float占4个字节
    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexBuffer;

    // 数组中每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 2;
    // 每个顶点的坐标数     X ,  Y
    static float triangleCoords[] = {
            0.0f,  0.5f ,    // top
            -0.5f, -0.5f ,   // bottom left
            0.5f, -0.5f };   // bottom right

    // 着色器 program的id
    private int program;

    //------------第一步 : 定义两个标签，分别于着色器代码中的变量名相同,
    //------------第一个是顶点着色器的变量名，第二个是片段着色器的变量名
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";

    //------------第二步: 定义两个ID,我们就是通ID来实现数据的传递的,这个与前面
    //------------获得program的ID的含义类似的
    private int uColorLocation;
    private int aPositionLocation;

    //---------第四步:定义坐标元素的个数，这里有三个顶点
    private static final int POSITION_COMPONENT_COUNT = 3;

    public Triangle(Context context){
        this.context = context;

        // 初始化 FloatBuffer 的参数
        vertexBuffer = ByteBuffer
                .allocateDirect(triangleCoords.length * BYTES_PER_FLOAT) // float占4个字节
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(triangleCoords);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);
        getProgram();


        //----------第三步: 获取这两个ID ，是通过前面定义的标签获得的
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

        //---------第五步: 传入数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    //获取program的id
    private void getProgram(){
        //获取顶点着色器文本
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.triangle_vertex_shader);
        //获取片段着色器文本
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);
        //获取program的id
        program = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(program);
    }
    //----------第七步:绘制
    public void draw(){
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, POSITION_COMPONENT_COUNT);
    }
}
