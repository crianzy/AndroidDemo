package czycom.testxfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/12/3.
 */
public class MyText extends TextView {
    public static final String TAG = "MyText";

    public MyText(Context context) {
        super(context);
        init();
    }

    public MyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private Rect mRect;

    PorterDuffXfermode mPorterDuffXfermode;

    private void init() {
        setTextColor(Color.BLUE);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mPaint.setXfermode(mPorterDuffXfermode);

        mRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d(TAG, "getWidth = " + getWidth() + " , getHeight() = " + getHeight());
        int saveLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), getPaint(), Canvas.ALL_SAVE_FLAG);

        mRect.set(0, 0, 900, 200);
        canvas.drawRect(mRect, mPaint);

        canvas.restoreToCount(saveLayerCount);

    }
}
