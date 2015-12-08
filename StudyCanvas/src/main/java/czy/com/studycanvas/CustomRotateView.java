package czy.com.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 15/12/3.
 */
public class CustomRotateView extends View {

    public CustomRotateView(Context context) {
        super(context);
        init();
    }

    public CustomRotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mCirclePaint;
    Paint mLinePaint;
    Paint mBgPaint;

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int px = 500;
        int py = 500;

        canvas.drawRect(0, 0, px, py, mBgPaint);

        canvas.save();

        canvas.rotate(90, px / 2, py / 2);
        canvas.drawLine(px / 2, 0, 0, py / 2, mLinePaint);
        canvas.drawLine(px / 2, 0, px, py / 2, mLinePaint);// 右边的斜杠
        canvas.drawLine(px / 2, 0, px / 2, py, mLinePaint);// 垂直的竖杠
        canvas.restore();
        canvas.drawCircle(px - 100, py - 100, 50, mCirclePaint);
    }
}