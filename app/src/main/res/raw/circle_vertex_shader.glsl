uniform mat4 u_Matrix;
attribute vec4 a_Position;

void main()
{
    gl_Position = u_Matrix * a_Position;//u_Matrix即我们的4X4的投影矩阵
}