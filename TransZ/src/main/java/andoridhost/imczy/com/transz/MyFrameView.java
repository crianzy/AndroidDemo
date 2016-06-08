package andoridhost.imczy.com.transz;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chenzhiyong on 16/5/19.
 */
public class MyFrameView extends RelativeLayout {

    TextView mTextView;

    public MyFrameView(Context context) {
        super(context);
        init();
    }



    private void init() {
        mTextView = new TextView(getContext());
        mTextView.setText("asdasdas");
        setBackgroundColor(Color.WHITE);
        addView(mTextView);
    }
}
