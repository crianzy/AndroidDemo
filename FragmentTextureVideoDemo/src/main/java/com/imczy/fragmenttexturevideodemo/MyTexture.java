package com.imczy.fragmenttexturevideodemo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;

import com.imczy.common_util.io.IOUtil;
import com.imczy.common_util.log.LogUtil;

import java.io.File;

/**
 * Created by chenzhiyong on 15/10/28.
 */
public class MyTexture extends TextureView implements TextureView.SurfaceTextureListener {
    public static final String TAG = "MyTexture";


    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private String mVideoPath;

    public MyTexture(Context context) {
        super(context);
        init();
    }

    public MyTexture(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTexture(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        mVideoPath = IOUtil.getBaseLocalLocation(getContext()) + File.separator + "test.mp4";
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
        if (mMediaPlayer == null) {
            playVideo();
        } else {
            mMediaPlayer.setSurface(mSurface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    private void playVideo() {
        File file = new File(mVideoPath);
        if (!file.exists()) {
            return;
        }
        try {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(file.getAbsolutePath());
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    LogUtil.e(TAG, "onPrepared");
                    mMediaPlayer.start();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LogUtil.e(TAG, "onCompletion");
                }
            });

            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
