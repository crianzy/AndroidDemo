package com.czy.reecycleviewheader;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imczy.common_util.adapter.RecycleViewAdapter;

/**
 * Created by chenzhiyong on 15/12/6.
 */
public class MyFragment extends Fragment implements ScrollableHelper.ScrollableContainer {
    public final static String TAG = "MyFragment";
    public final static String SaveInstanceStateEXTRA = "SaveInstanceStateEXTRA";


    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler = new Handler();

    public static MyFragment getInstances() {
        MyFragment myFragment = new MyFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String str = "";
        if (savedInstanceState != null) {
            str = savedInstanceState.getString("SaveInstanceStateEXTRA");
        }
        // 这里必须是null
        View item = inflater.inflate(R.layout.item_1, null);
        mRecyclerView = (RecyclerView) item.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) item.findViewById(R.id.refresh);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new RecycleViewAdapter(getContext()));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
        return item;
    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }
}
