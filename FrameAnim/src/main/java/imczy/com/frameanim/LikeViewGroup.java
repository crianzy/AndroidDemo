package imczy.com.frameanim;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by chenzhiyong on 16/6/27.
 */
public class LikeViewGroup extends FrameLayout {

    public LikeViewGroup(Context context) {
        super(context);
    }

    public LikeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private ImageView mImageView;

    private void init() {
        mImageView = new ImageView(getContext());
        mImageView.setBackground(getResources().getDrawable(R.drawable.spinner));
        addView(mImageView);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(0, 0, getWidth(), getWidth());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 200 * 3;// 200dp

        setMeasuredDimension(widthMeasureSpec, height);
    }

    public void start() {
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageView.getBackground();
        animationDrawable.start();
        mImageView.animate().translationY(100*3).start();
    }
}
