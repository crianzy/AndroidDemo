package com.zuimeia.indexscrollbae;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 15/11/11.
 */
public class IndexScrollBar extends LinearLayout {
    public static final String TAG = "IndexScrollBar";

    public IndexScrollBar(Context context) {
        super(context);
        init();
    }

    public IndexScrollBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndexScrollBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private List<TextView> mTextViewList;
    private List<Rect> mRectList;
    private List<TextViewModel> mTextViewModelList;

    private void init() {
        this.setOrientation(VERTICAL);
        mTextViewList = new ArrayList<>();
        mRectList = new ArrayList<>();
        mTextViewModelList = new ArrayList<>();
        setGravity(Gravity.RIGHT);

        for (int i = 0; i <= 12; i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setText(i + "");
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TextViewModel textViewModel = new TextViewModel();
//            textView.setPivotX(0);
//            textView.setPivotY(0);
            int padding = 20;
//            if (i < 6) {
//                padding = (int) (((i % 6 + 1) / 7.0f) * 60);
//            } else {
//                padding = (int) ((((12 - i) % 6 + 1) / 7.0f) * 60);
//            }
            textViewModel.padding = padding;
            if (i % 2 == 0) {
                textView.setPadding(0, padding, 30, padding);
            } else {
                textView.setPadding(30, padding, 0, padding);
            }

            LogUtil.d(TAG, "i % 6 = " + ((i % 6 + 1) / 7.0f) + " , (12 - i) % 6 = " + ((12 - i + 1) % 6 + 1) + " , padding = " + padding);

//            float rotation = ((i % 6 + 1) / 7.0f) * 30;
//            if (i > 6) {
//                rotation = -rotation;
//            }
//            textView.setRotation(rotation);

            mTextViewModelList.add(textViewModel);
            mTextViewList.add(textView);
            addView(textView, layoutParams);
        }

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mRectList.clear();
                for (int i = 0; i <= 12; i++) {
                    Rect rect = new Rect();
                    mTextViewList.get(i).getGlobalVisibleRect(rect);
                    mTextViewList.get(i).setPivotX(mTextViewList.get(i).getWidth());
                    mTextViewList.get(i).setPivotY(mTextViewList.get(i).getHeight()/2);
                    mRectList.add(rect);
                }
            }
        });
    }


    private void caluTextModel(int nowIndex) {
        for (int i = 0; i <= 12; i++) {
            if (i < nowIndex) {

            } else if (i == nowIndex) {

            } else {

            }
        }
    }

    private int lastIndex = -1;

    private int lastX;
    private int lastY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int nowIndex = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                for (int i = 0; i <= 12; i++) {
                    if (mRectList.get(i).contains(lastX, lastY)) {
                        nowIndex = i;
                        break;
                    }
                }

                if (nowIndex == -1) {
                    return true;
                } else if (nowIndex == lastIndex) {
                    return true;
                } else {
                    if (lastIndex != -1) {
                        mTextViewList.get(lastIndex).setScaleX(1);
                        mTextViewList.get(lastIndex).setScaleY(1);
                        mTextViewList.get(lastIndex).setTranslationX(0);
                    }

                    mTextViewList.get(nowIndex).setScaleX(3f);
                    mTextViewList.get(nowIndex).setScaleY(3f);
                    if(nowIndex % 2 != 0){
                        mTextViewList.get(lastIndex).setTranslationX(30);
                    }
                    lastIndex = nowIndex;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                if (lastIndex != -1) {

                }
                for (int i = 0; i <= 12; i++) {
                    if (mRectList.get(i).contains(lastX, lastY)) {
                        nowIndex = i;
                        break;
                    }
                }

                if (nowIndex == -1) {
                    return true;
                } else if (nowIndex == lastIndex) {
                    return true;
                } else {
                    if (lastIndex != -1) {
                        mTextViewList.get(lastIndex).setScaleX(1);
                        mTextViewList.get(lastIndex).setScaleY(1);
                        mTextViewList.get(lastIndex).setTranslationX(0);
                    }

                    mTextViewList.get(nowIndex).setScaleX(3f);
                    mTextViewList.get(nowIndex).setScaleY(3f);
                    lastIndex = nowIndex;
                    if(nowIndex % 2 != 0){
                        mTextViewList.get(lastIndex).setTranslationX(30);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                if (lastIndex != -1) {
//                    mTextViewList.get(lastIndex).setScaleX(1);
//                    mTextViewList.get(lastIndex).setScaleY(1);
//                }
                break;
        }
        return true;
    }

    public class TextViewModel {
        int transX = 0;
        int rotation = 0;
        float scale = 1f;
        int padding = 0;
    }
}
