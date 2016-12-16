package imczy.com.activitystartsequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by chenzhiyong on 16/6/28.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityB.class));
            }
        });
        Log.w(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() called with: " + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() called with: " + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop() called with: " + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy() called with: " + "");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.w(TAG, "onSaveInstanceState() called with: " + "outState = [" + outState + "]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.w(TAG, "onRestart() called with: " + "");
    }
}
