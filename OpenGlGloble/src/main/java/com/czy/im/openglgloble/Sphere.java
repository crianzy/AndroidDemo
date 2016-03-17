package com.czy.im.openglgloble;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.Matrix;

public class Sphere {

    public int textureId;// ����ID

    public float angleX = 0;
    public float angleY = 0;
    // The colors mapped to the vertices.
    float[] colors = { 0f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f, 1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 1f, 1f,
                    1f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 0f, 1f, 0f, 1f, 0f, 0f, 1f, 1f, 1f, 0f, 1f, 1f

    };
    private FloatBuffer colorBuffer;

    public void draw(GL10 gl) {
        float theta, pai;
        float co, si;
        float r1, r2;
        float h1, h2;
        float step = 2.0f;
        float[][] v = new float[32][3];
        ByteBuffer vbb;
        FloatBuffer vBuf;

        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

//        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glFrontFace(GL10.GL_CCW);

        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glCullFace(GL10.GL_BACK);
        gl.glPopMatrix();
        float[] modelview = new float[16];
        ((GL11) gl).glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelview, 0); // ��ȡ��ǰ����
        float[] x_axis = { 1, 0, 0, 0 };
        float[] y_axis = { 0, 1, 0, 0 };
        Matrix.invertM(modelview, 0, modelview, 0); // �������
        Matrix.multiplyMV(x_axis, 0, modelview, 0, x_axis, 0); // ��ȡ����x����ģ�����ϵ���ָ��w�ᣩ
        Matrix.multiplyMV(y_axis, 0, modelview, 0, y_axis, 0);

        gl.glRotatef(angleX, y_axis[0], y_axis[1], y_axis[2]);
        gl.glRotatef(angleY, x_axis[0], x_axis[1], x_axis[2]);
        gl.glPushMatrix();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        for (pai = -90.0f; pai < 90.0f; pai += step) {
            int n = 0;

            r1 = (float) Math.cos(pai * Math.PI / 180.0);
            r2 = (float) Math.cos((pai + step) * Math.PI / 180.0);
            h1 = (float) Math.sin(pai * Math.PI / 180.0);
            h2 = (float) Math.sin((pai + step) * Math.PI / 180.0);

            for (theta = 0.0f; theta <= 360.0f; theta += step) {
                co = (float) Math.cos(theta * Math.PI / 180.0);
                si = -(float) Math.sin(theta * Math.PI / 180.0);

                v[n][0] = (r2 * co);
                v[n][1] = (h2);
                v[n][2] = (r2 * si);
                v[n + 1][0] = (r1 * co);
                v[n + 1][1] = (h1);
                v[n + 1][2] = (r1 * si);

                vBuf.put(v[n]);
                vBuf.put(v[n + 1]);

                n += 2;

                if (n > 31) {
                    vBuf.position(0);
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

                    n = 0;
                    theta -= step;
                }
            }
            vBuf.position(0);

//            gl.glColor4f(Math.abs(pai / 90f), Math.abs(pai / 90f), Math.abs(pai / 90f), 1.0f);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        float speed = 5;
        angleX -= speed;
        angleY -= speed;
        if (angleX < 0)
            angleX = 0;
        if (angleY < 0)
            angleY = 0;
    }
}
