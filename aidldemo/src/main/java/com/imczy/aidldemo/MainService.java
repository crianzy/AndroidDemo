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
			// 这个方法 不是在主线程调用的
			//  thread = Thread[Binder_2,5,main]
			// binder 的时候 具体的 binder 方法不是在主线程执行
			Log.e(TAG, "onClick: getPid  thread = " + Thread.currentThread() + " , pid = " + android.os.Process.myPid());
			return android.os.Process.myPid();
		}

		public void addBook(Book book) {

		}

		@Override
		public void waitToReady() throws RemoteException {

			// 虽说 这里不是主线程, 但是如果主线程 通过 IPC 调用 这个方法, 那么也会造成线程等待
			// 因为主线程 通过 IPC 调用了 binder 的方法, 然后就会处于阻塞状态,  知道 方法执行完成 然后才会 notify 主线程
			Log.e(TAG, "onClick: waitToReady  thread = " + Thread.currentThread() + " , pid = " + android.os.Process.myPid());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

}
