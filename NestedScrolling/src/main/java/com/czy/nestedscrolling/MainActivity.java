package com.czy.nestedscrolling;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MyAdpater mMyAdpater;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        mMyAdpater = new MyAdpater(this);
        mRecyclerView.setAdapter(mMyAdpater);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


        mRecyclerView.setLayoutManager(layoutManager);
    }
}
