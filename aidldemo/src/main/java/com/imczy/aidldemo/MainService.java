package com.imczy.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/27.
 */
public class MainService extends Service {
	public static final String TAG = "MainService";

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.d(TAG, "onCreate");
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.d(TAG, "onBind");
		Log.e(TAG, "onClick: onBind  thread = " + Thread.currentThread() + " , pid = " + android.os.Process.myPid());
		return mBinder;
	}

	private IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
		}

		@Override
		public int getPid() throws RemoteException {
			// 返回当前 service 的pic
			LogUtil.d(TAG, "getPid");
			Log.e(TAG, "onClick: getPid  thread = " + Thread.currentThread() + " , pid = " + android.os.Process.myPid());
			return android.os.Process.myPid();
		}

		public void addBook(Book book) {

		}

		@Override
		public void waitToReady() throws RemoteException {

			Log.e(TAG, "onClick: waitToReady  thread = " + Thread.currentThread() + " , pid = " + android.os.Process.myPid());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

}
