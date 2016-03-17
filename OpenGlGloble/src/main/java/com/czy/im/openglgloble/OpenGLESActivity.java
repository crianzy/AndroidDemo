package com.czy.im.openglgloble;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class OpenGLESActivity extends Activity implements IOpenGLDemo {
    private static final String TAG = "OpenGLESActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(new OpenGLRenderer(this));
        setContentView(mGLSurfaceView);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    protected GLSurfaceView mGLSurfaceView;

    @Override
    public void initLight(GL10 gl) {

    }

    @Override
    public void DrawScene(GL10 gl) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * ���ð�ɫ�ƹ�
     * 
     * @param gl
     * @param cap
     * @param posX
     * @param posY
     * @param posZ
     */
    public void initWhiteLight(GL10 gl, int cap, float posX, float posY, float posZ) {
        gl.glEnable(cap);// ��cap�ŵ�

        // ����������
        float[] ambientParams = { 1.0f, 1.0f, 1.0f, 1.0f };// ����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

        // ɢ�������
        float[] diffuseParams = { 1.0f, 1.0f, 1.0f, 1.0f };// ����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

        // ���������
        float[] specularParams = { 1f, 1f, 1f, 1.0f };// ����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

        float[] positionParams = { posX, posY, posZ, 1 };
        gl.glLightfv(cap, GL10.GL_POSITION, positionParams, 0);
    }

    /**
     * ��һ��bitmap ����һ������
     * 
     * bitmap�Ĵ�С���ƣ�
     * 
     * @param gl
     * @param resourceId
     * @return
     */
    public int initTexture(GL10 gl, int resourceId) {
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        int currTextureId = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

        InputStream is = this.getResources().openRawResource(resourceId);
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle();
        return currTextureId;
    }

    /**
     * ��ʼ����ɫ����
     * 
     * ����Ϊ��ɫʱʲô��ɫ�Ĺ���������ͽ����ֳ�ʲô��ɫ
     * 
     * @param gl
     */
    private void initMaterial(GL10 gl) {

        // ������Ϊ��ɫ����
        float ambientMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial, 0);

        // ɢ���Ϊ��ɫ����
        float diffuseMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial, 0);

        // �߹����Ϊ��ɫ
        float specularMaterial[] = { 1f, 1f, 1f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial, 0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
    }

    @Override
    public void initObject(GL10 gl) {
        // TODO Auto-generated method stub

    }

}
