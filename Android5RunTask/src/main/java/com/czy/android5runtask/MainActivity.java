package com.czy.android5runtask;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsageStatsManager statsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();

        List<UsageStats> usageStatses = statsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - (1000 * 60 * 60 * 3), time);
        Log.d(TAG, "onCreate: usageStatses = " + usageStatses);
        for (int i = 0, z = usageStatses.size(); i < z; i++) {
            UsageStats usageStats = usageStatses.get(i);
            Log.d(TAG, "onCreate: usageStats getPackageName = " + usageStats.getPackageName() + " , getPackageName = " + usageStats.getFirstTimeStamp() + " , getPackageName = " + usageStats.getLastTimeStamp());

        }


        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
