package com.squareup.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by chenzhiyong on 16/3/24.
 */
public class BackgroundService extends Service {
	private static final String TAG = "BackgroundService";


	@Override
	public void onCreate() {
		super.onCreate();
		Intent i = new Intent(this, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		final Notification notification = builder
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("121212")
				.setContentText("12123")
				.setOngoing(true)
				.setContentIntent(contentIntent)
				.build(); // 建立通知
		startForeground(0, notification);

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand: intent = " + intent);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		stopForeground(false);
		super.onDestroy();
	}
}
