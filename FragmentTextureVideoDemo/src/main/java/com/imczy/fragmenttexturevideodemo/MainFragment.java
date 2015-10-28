package com.imczy.fragmenttexturevideodemo;

import android.app.Fragment;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.imczy.common_util.io.IOUtil;
import com.imczy.common_util.log.LogUtil;

import java.io.File;

/**
 * Created by chenzhiyong on 15/10/28.
 */
public class MainFragment extends Fragment implements TextureView.SurfaceTextureListener {
    public static final String TAG = "MainFragment";


    private TextureView mTextureView;
    private String mVideoPath;

    // test.mp4
    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment, null);
        mTextureView = (TextureView) view.findViewById(R.id.texture);
        mVideoPath = IOUtil.getBaseLocalLocation(getActivity()) + File.separator + "test.mp4";
        LogUtil.d(TAG, "mVideoPath = " + mVideoPath);
        mTextureView.setSurfaceTextureListener(this);
        return view;
    }

    private Surface mSurface;
    private MediaPlayer mMediaPlayer;

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
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

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
