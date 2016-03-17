package com.czy.expandlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_list);
        mExpandableListView.setAdapter(new ExpandListViewAdapter(this));
        mExpandableListView.setChildIndicator(getResources().getDrawable(R.drawable.icon_add_follow_normal));
        mExpandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.icon_approve_pressed));


    }
}
