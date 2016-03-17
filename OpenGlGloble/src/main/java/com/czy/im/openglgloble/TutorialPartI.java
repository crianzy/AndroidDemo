package com.czy.im.openglgloble;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TutorialPartI extends OpenGLESActivity implements IOpenGLDemo {

    float eyeX = 0f;
    float eyeY = 0f;
    float eyeZ = 4f;

    Sphere sphere = new Sphere();

    Ball ball = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLSurfaceView.setOnTouchListener(onTouchListener);
    }

    @Override
    public void initObject(GL10 gl) {
        ball = new Ball(5, initTexture(gl, R.drawable.logo));
    }

    @Override
    public void initLight(GL10 gl) {
        gl.glEnable(GL10.GL_LIGHTING);
        initWhiteLight(gl, GL10.GL_LIGHT0, 0.5f, 0.5f, 0.5f);
    }

    @Override
    public void DrawScene(GL10 gl) {
        super.DrawScene(gl);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        ball.drawSelf(gl);
        gl.glPopMatrix();

    }

    private OnTouchListener onTouchListener = new OnTouchListener() {
        float lastX, lastY;

        private int mode = 0; // ���ص�ĸ���

        float oldDist = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = 1;
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    mode += 1;

                    oldDist = caluDist(event);

                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    mode -= 1;
                    break;

                case MotionEvent.ACTION_UP:
                    mode = 0;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode >= 2) {
                        float newDist = caluDist(event);
                        if (Math.abs(newDist - oldDist) > 2f) {
                            zoom(newDist, oldDist);
                        }
                    } else {
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;

                        float a = 180.0f / 320;
                        ball.mAngleX += dx * a;
                        ball.mAngleY += dy * a;
                    }
                    break;
            }

            lastX = (int) event.getRawX();
            lastY = (int) event.getRawY();
            return true;
        }
    };

    public void zoom(float newDist, float oldDist) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float px = displayMetrics.widthPixels;
        float py = displayMetrics.heightPixels;

        ball.zoom += (newDist - oldDist) * (ball.maxZoom - ball.minZoom) / Math.sqrt(px * px + py * py) / 4;

        if (ball.zoom > ball.maxZoom) {
            ball.zoom = ball.maxZoom;
        } else if (ball.zoom < ball.minZoom) {
            ball.zoom = ball.minZoom;
        }
    }

    public float caluDist(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
