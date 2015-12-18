package com.zuimeia.android5activityanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    private ViewGroup mRootView;
    private View mRedBox, mGreenBox, mBlueBox, mBlackBox;

    ImageView mImageView;
    Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set an enter transition
        getWindow().setEnterTransition(new Explode());
        // set an exit transition
        getWindow().setExitTransition(new Slide());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = (ViewGroup) findViewById(R.id.layout_root_view);
        mRedBox = findViewById(R.id.red_box);
        mGreenBox = findViewById(R.id.green_box);
        mBlueBox = findViewById(R.id.blue_box);
        mBlackBox = findViewById(R.id.black_box);
        mImageView = (ImageView) findViewById(R.id.img);

        mImageView.setClipToOutline(true);


//        mRootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(mRootView, new Fade());
//                toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
//            }
//        });

        mIntent = new Intent(this, Activity2.class);

        mRedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair<View, String> p1 = Pair.create(mRedBox, "red_box");
                Pair<View, String> p2 = Pair.create(mGreenBox, "green_box");
                Pair<View, String> p3 = Pair.create(mBlueBox, "blue_box");
                Pair<View, String> p4 = Pair.create(mBlackBox, "black_box");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p1, p2, p3, p4);
                startActivity(mIntent, activityOptions.toBundle());
            }
        });

        mGreenBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the center for the clipping circle
                int cx = mGreenBox.getWidth() / 2;
                int cy = mGreenBox.getHeight() / 2;
                // get the initial radius for the clipping circle
                int initialRadius = mGreenBox.getWidth();
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(mGreenBox, cx, cy, initialRadius, 0);

                // make the view invisible when the animation is done
//                anim.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        mGreenBox.setVisibility(View.INVISIBLE);
//                    }
//                });

                // start the animation
                anim.start();
            }
        });

        mBlueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(mIntent, null);
            }
        });

        mBlackBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.d("mBlackBox  onClick");
                ObjectAnimator mAnimator;
                Path path = new Path();
                path.lineTo(0.3f, 0.4f);
                path.moveTo(0.15f, 0.25f);
                path.lineTo(1f, 1f);
                PathInterpolator pathInterpolator = new PathInterpolator(0.27f, 2.34f, 0.49f, -0.91f);
                mAnimator = ObjectAnimator.ofFloat(mBlackBox, View.TRANSLATION_Y, mBlackBox.getTranslationY(), 200);
                mAnimator.setInterpolator(pathInterpolator);
                mAnimator.start();
            }
        });
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
