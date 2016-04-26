package com.squareup.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	Button mButton1;
	Button mButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton1 = (Button) findViewById(R.id.btn1);
		mButton2 = (Button) findViewById(R.id.btn2);

		mButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showNotification2();
			}
		});

		mButton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Activity2.class);
				startActivity(intent);
			}
		});
	}

	private void startService() {
		Intent intent = new Intent(this, BackgroundService.class);
		startService(intent);
	}


	private void showNotification1() {
		final int notifyID = 1; // 通知的識別號碼
		final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
		final Notification notification = new Notification
				.Builder(getApplicationContext())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("內容標題")
				.setContentText("內容文字")
				.build(); // 建立通知

		notificationManager.notify(notifyID, notification); // 發送通知
	}


	private void showNotification2() {
		Log.e(TAG, "showNotification: ");
//		Intent i = new Intent(this, MainActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setClass(this, Activity2.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		final Notification notification = builder
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("內容標題1123123")
				.setContentText("內容文字123")
				.setOngoing(true)
				.setContentIntent(contentIntent)
				.setDeleteIntent(contentIntent)
				.build(); // 建立通知

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(123, notification);
	}
}
