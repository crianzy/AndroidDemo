package com.imczy.bindstartservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/29.
 */
public class MyService extends Service {
	public static final String TAG = "MyService";

	private IBinder mIBinder;

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.d(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		LogUtil.d(TAG, "onStart");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.d(TAG, "onStartCommand");
		Notification notification = new Notification();
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//		notification.setLatestEventInfo(this, getText(R.string.notification_title),
//				getText(R.string.notification_message), pendingIntent);
		startForeground(0, notification);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, "onDestroy");
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		LogUtil.d(TAG, "onRebind");
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.d(TAG, "onBind");
		if (mIBinder == null) {
			mIBinder = new ServiceBinder();
		}

		return mIBinder;
	}

	class ServiceBinder extends Binder {
		public MyService getService() {
			return MyService.this;
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
}
