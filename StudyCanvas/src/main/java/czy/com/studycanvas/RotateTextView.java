package czy.com.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by chenzhiyong on 15/12/3.
 */
public class RotateTextView extends TextView {


    public RotateTextView(Context context) {
        super(context);
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(30);
        canvas.translate(100, 30);
        canvas.drawCircle(0, 0, 30, mPaint);
        super.onDraw(canvas);
    }
}
