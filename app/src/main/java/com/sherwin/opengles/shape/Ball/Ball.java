package com.sherwin.opengles.shape.Ball;

import android.content.Context;
import android.opengl.GLES20;

import com.sherwin.opengles.R;
import com.sherwin.opengles.utils.MatrixState;
import com.sherwin.opengles.utils.ShaderHelper;
import com.sherwin.opengles.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/16.17:52
 * @desc
 */
public class Ball {
    private Context context;

    private static final float UNIT_SIZE = 1.0f;// 单位尺寸
    private float r = 0.6f; // 球的半径
    final int angleSpan = 10;// 将球进行单位切分的角度
    private FloatBuffer vertexBuffer;// 顶点坐标
    int vCount = 0;// 顶点个数，先初始化为0
    // float类型的字节数
    private static final int BYTES_PER_FLOAT = 4;
    // 数组中每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;

    private int program;
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private int uMatrixLocation;
    private int aPositionLocation;

    public Ball(Context context){
        this.context = context;
        initVertexData();
        getProgram();
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        //---------传入顶点数据数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    //获取program
    private void getProgram(){
        //获取顶点着色器文本
        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.ball_vertex_shader);
        //获取片段着色器文本
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.ball_fragment_shader);
        //获取program的id
        program = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(program);
    }

    public void draw(){
        //将最终变换矩阵写入
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, MatrixState.getFinalMatrix(),0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

    public void initVertexData() {
        ArrayList<Float> alVertix = new ArrayList<Float>();// 存放顶点坐标的ArrayList
        for (int vAngle = 0; vAngle < 180; vAngle = vAngle + angleSpan)// 垂直方向angleSpan度一份
        {
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan)// 水平方向angleSpan度一份
            {
                // 纵向横向各到一个角度后计算对应的此点在球面上的坐标
                float x0 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle)));
                float y0 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle)));
                float z0 = (float) (r * UNIT_SIZE * Math.cos(Math
                        .toRadians(vAngle)));
                // Log.w("x0 y0 z0","" + x0 + "  "+y0+ "  " +z0);

                float x1 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle + angleSpan)));
                float y1 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle + angleSpan)));
                float z1 = (float) (r * UNIT_SIZE * Math.cos(Math
                        .toRadians(vAngle)));
                // Log.w("x1 y1 z1","" + x1 + "  "+y1+ "  " +z1);

                float x2 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float) (r * UNIT_SIZE * Math.cos(Math
                        .toRadians(vAngle + angleSpan)));
                // Log.w("x2 y2 z2","" + x2 + "  "+y2+ "  " +z2);
                float x3 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle)));
                float y3 = (float) (r * UNIT_SIZE
                        * Math.sin(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle)));
                float z3 = (float) (r * UNIT_SIZE * Math.cos(Math
                        .toRadians(vAngle + angleSpan)));
                // Log.w("x3 y3 z3","" + x3 + "  "+y3+ "  " +z3);
                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
                alVertix.add(x0);
                alVertix.add(y0);
                alVertix.add(z0);

                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
            }
        }
        vCount = alVertix.size() / COORDS_PER_VERTEX;// 顶点的数量
        // 将alVertix中的坐标值转存到一个float数组中
        float vertices[] = new float[vCount * COORDS_PER_VERTEX];
        for (int i = 0; i < alVertix.size(); i++) {
            vertices[i] = alVertix.get(i);
        }
        vertexBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(vertices);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);
    }
}
