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
public class ActivityB extends AppCompatActivity {
    private static final String TAG = "ActivityB";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityB.this, MainActivity.class));
            }
        });

        Log.e(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called with: " + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called with: " + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called with: " + "");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e(TAG, "onSaveInstanceState() called with: " + "outState = [" + outState + "]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e(TAG, "onRestart() called with: " + "");
    }
}
