package com.czy.androiddialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(MainActivity.this);
//                builder.setTitle("title");
//                builder.setMessage("message");
//                builder.setPositiveButton("Positive", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e(TAG, "onClick: Positive");
//                    }
//                });
////                builder.setNegativeButton("Negative", new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Log.e(TAG, "onClick: Negative");
////                    }
////                });
//                builder.setNeutralButton("Neutral", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e(TAG, "onClick: Neutral");
//                    }
//                });
//                builder.show();

                CustomAlertDialog2 customAlertDialog2 = new CustomAlertDialog2(MainActivity.this);
                customAlertDialog2.show();
            }
        });

    }
}
