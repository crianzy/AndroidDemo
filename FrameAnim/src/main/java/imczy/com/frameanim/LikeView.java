package imczy.com.frameanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenzhiyong on 16/6/27.
 */
public class LikeView extends View {

    private static final String TAG = "LikeView";

    public LikeView(Context context) {
        super(context);
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    AnimationDrawable mAnimationDrawable;
    Rect animRect;

    private void init() {
        mAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.spinner);

        Log.e(TAG, "init: mAnimationDrawable = " + mAnimationDrawable);
        animRect = mAnimationDrawable.getBounds();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 200 * 3;// 200dp

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mAnimationDrawable.setBounds(0, 0, getWidth(), getWidth());
        mAnimationDrawable.draw(canvas);
    }


    public void statr() {
        Log.e(TAG, "statr: ");
        if (mAnimationDrawable != null) {
            mAnimationDrawable.start();
            invalidate();
        }
    }
}
