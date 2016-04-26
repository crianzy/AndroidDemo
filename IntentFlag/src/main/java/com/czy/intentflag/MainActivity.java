package com.czy.intentflag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	TextView mTextView;
	Button btn1;
	Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.txt);

		Log.e(TAG, "MainActivity onCreate taskId = " + getTaskId());
		mTextView.setText("AAAA");
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);

		btn1.setText("mormal");
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityB.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

				startActivity(intent);
			}
		});

		btn2.setText("clean top");
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityB.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			}
		});

	}
}
