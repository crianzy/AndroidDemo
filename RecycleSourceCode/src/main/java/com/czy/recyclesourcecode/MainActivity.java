package com.czy.recyclesourcecode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v71.widget.LinearLayoutManager;
import android.support.v71.widget.RecyclerView;


/**
 * Created by chenzhiyong on 16/1/19.
 */
public class MainActivity extends Activity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecycleViewAdapter(this));
    }
}
