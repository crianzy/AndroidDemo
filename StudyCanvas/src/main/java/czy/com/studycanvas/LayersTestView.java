package czy.com.studycanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/12/3.
 */
public class LayersTestView extends View {
    public static final String TAG = "LayersTestView";

    private static final int LAYER_FLAGS =
            Canvas.MATRIX_SAVE_FLAG
                    | Canvas.CLIP_SAVE_FLAG
                    | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                    | Canvas.CLIP_TO_LAYER_SAVE_FLAG

            ;

    private Paint mPaint;

    public LayersTestView(Context context) {
        super(context);
        init();
    }

    public LayersTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayersTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        int count = canvas.saveLayer(0, 0, 300, 300, mPaint, LAYER_FLAGS);
//        canvas.drawColor(Color.WHITE);
//        canvas.translate(10, 50);
//        mPaint.setColor(Color.RED);
//        canvas.drawCircle(100, 100, 100, mPaint);
//
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        mPaint.setColor(Color.BLUE);
//        canvas.drawCircle(180, 180, 100, mPaint);
//        canvas.restoreToCount(count);
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(75, 75, 75, mPaint);

        canvas.translate(25, 25);
        canvas.saveLayerAlpha(0, 0, 200, 200, 0x88, Canvas.ALL_SAVE_FLAG);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(125, 125, 75, mPaint);

        canvas.saveLayer(0, 0, 200, 200, mPaint, LAYER_FLAGS);
        canvas.drawCircle(30, 30, 30, mPaint);

    }
}
