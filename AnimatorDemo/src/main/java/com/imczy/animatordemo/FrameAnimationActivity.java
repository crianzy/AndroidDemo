package com.imczy.animatordemo;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by chenzhiyong on 15/10/30.
 */
public class FrameAnimationActivity extends Activity {

    ImageView mImageView;
    AnimationDrawable mLoadingAnim;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        mImageView = (ImageView) findViewById(R.id.img);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


//        mImageView.setBackgroundResource(R.drawable.loading);
//        mLoadingAnim = (AnimationDrawable) mImageView.getBackground();

        mLoadingAnim = new AnimationDrawable();
        for (int i = 1; i <= 8; i++) {
            int id = getResources().getIdentifier("loading_" + i, "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            mLoadingAnim.addFrame(drawable, 60);
        }
        mLoadingAnim.setOneShot(false);
        mImageView.setBackgroundDrawable(mLoadingAnim);
    }

    public void doStart(View view) {
        mLoadingAnim.start();
    }

    public void doStop(View view) {
        mLoadingAnim.stop();
    }
}
