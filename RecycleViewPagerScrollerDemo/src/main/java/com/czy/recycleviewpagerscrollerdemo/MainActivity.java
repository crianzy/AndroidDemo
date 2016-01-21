package com.czy.recycleviewpagerscrollerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.czy.recycleviewpagerscrollerdemo.snappy.SnappyLinearLayoutManager;
import com.czy.recycleviewpagerscrollerdemo.snappy.SnappyRecyclerView;

/**
 * Created by chenzhiyong on 16/1/19.
 */
public class MainActivity extends AppCompatActivity {

    SnappyRecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (SnappyRecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new SnappyLinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecycleViewAdapter(this));

    }
}
