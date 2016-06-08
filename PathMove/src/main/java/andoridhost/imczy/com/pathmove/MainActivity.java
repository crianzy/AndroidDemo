package andoridhost.imczy.com.pathmove;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ImageView mImageView;
    View mBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.img);
        mBox = findViewById(R.id.box);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPathAnima2();
            }
        });
    }


    private void doPathAnima() {
        Rect imgRect = new Rect();
        mImageView.getGlobalVisibleRect(imgRect);

        Rect boxRect = new Rect();
        mBox.getGlobalVisibleRect(boxRect);

        int transY = imgRect.centerY() - boxRect.centerY();
        int transX = imgRect.centerX() - boxRect.centerX();

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimatorY = ValueAnimator.ofInt(0, -transY);
        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageView.setTranslationY((int) animation.getAnimatedValue());
            }
        });
        valueAnimatorY.setDuration(400);

        ValueAnimator valueAnimatorX = ValueAnimator.ofInt(0, -transX);
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageView.setTranslationX((int) animation.getAnimatedValue());
            }
        });
        valueAnimatorX.setDuration(250);

        animatorSet.playTogether(valueAnimatorY, valueAnimatorX);
        animatorSet.start();


    }
    private void doPathAnima2() {
        Rect imgRect = new Rect();
        mImageView.getGlobalVisibleRect(imgRect);

        Rect boxRect = new Rect();
        mBox.getGlobalVisibleRect(boxRect);

        int transY = imgRect.centerY() - boxRect.centerY();
        int transX = imgRect.centerX() - boxRect.centerX();

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator valueAnimatorY = ValueAnimator.ofInt(0, -transY);
        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageView.setTranslationY((int) animation.getAnimatedValue());
            }
        });

        ValueAnimator valueAnimatorX = ValueAnimator.ofInt(0, -transX);
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageView.setTranslationX((int) animation.getAnimatedValue());
            }
        });

        valueAnimatorX.setInterpolator(new FastOutSlowInInterpolator());
        animatorSet.playTogether(valueAnimatorY, valueAnimatorX);
        animatorSet.setDuration(3000);

        animatorSet.start();


    }
}
