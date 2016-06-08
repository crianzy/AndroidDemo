package andoridhost.imczy.com.clipdemo;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button clipDemo;
    Button animDemo;

    ImageView mImageView;

    Rect imageRect;

    Rect clipRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clipDemo = (Button) findViewById(R.id.clip_btn);
        animDemo = (Button) findViewById(R.id.anim_btn);
        mImageView = (ImageView) findViewById(R.id.img);

        imageRect = new Rect();
        clipRect = new Rect();


        clipDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.getLocalVisibleRect(imageRect);
                Log.e(TAG, "onClick: imageRect = " + imageRect);

                clipRect.set(imageRect);
                clipRect.bottom += 300;
                mImageView.setClipBounds(clipRect);

            }
        });

        animDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueAnimator valueAnimator = ValueAnimator.ofInt(clipRect.bottom, imageRect.bottom);
                valueAnimator.setDuration(900);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int val = (int) animation.getAnimatedValue();
                        clipRect.bottom = val;
                        mImageView.setClipBounds(clipRect);
                    }
                });
                valueAnimator.start();


            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
