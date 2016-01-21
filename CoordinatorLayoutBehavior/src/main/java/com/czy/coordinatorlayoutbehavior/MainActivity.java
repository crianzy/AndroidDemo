package com.czy.coordinatorlayoutbehavior;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.imczy.common_util.adapter.RecycleViewAdapter;
import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    RecyclerView mRecyclerView;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_behavior);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecycleViewAdapter(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LogUtil.d(TAG, "onScrolled dy = " + dy);
                mRecyclerView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                mRecyclerView.dispatchNestedPreScroll(dx, dy, new int[]{dx, dy}, null);
                mRecyclerView.dispatchNestedScroll(dx, dy, 0, 0, null);
                mRecyclerView.stopNestedScroll();
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("mHandler.postDelayed");
                int dx = 0;
                int dy = 300;
                mRecyclerView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                mRecyclerView.dispatchNestedPreScroll(dx, dy, new int[]{dx, dy}, null);
                mRecyclerView.dispatchNestedScroll(dx, dy, 0, 0, null);
                mRecyclerView.stopNestedScroll();
            }
        }, 3000);

    }
}

