package com.czy.recycleviewpagerscrollerdemo;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.imczy.common_util.PhoneUtil;
import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/19.
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    public static final String TAG = "MyLinearLayoutManager";
    private int screenHeight;

    public MyLinearLayoutManager(Context context) {
        super(context);
        screenHeight = PhoneUtil.getDisplayHeight(context) - PhoneUtil.getStatusBarHeight();
        LogUtil.d(TAG, "screenHeight = " + screenHeight);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        RecyclerView.SmoothScroller smoothScroller = new MyLinearLayoutManager.SmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }


    private class SmoothScroller extends LinearSmoothScroller {

        public SmoothScroller(Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return MyLinearLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDyToMakeVisible(View view, int snapPreference) {
            final RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (!layoutManager.canScrollVertically()) {
                return 0;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            final int top = layoutManager.getDecoratedTop(view) - params.topMargin;
            final int bottom = layoutManager.getDecoratedBottom(view) + params.bottomMargin;
            final int viewHeight = bottom - top;


            final int start = (screenHeight - viewHeight) / 2;
            LogUtil.d(TAG, "start = " + start);
            final int end = start + viewHeight;

            return calculateDtToFit(top, bottom, start, end, snapPreference);
        }
    }
}
