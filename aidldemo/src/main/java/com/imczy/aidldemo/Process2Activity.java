package com.imczy.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/27.
 */
public class Process2Activity extends Activity {
	public static final String TAG = "Process2Activity";

	TextView mProcessTxt;
	private Button mLinkMainProcessBtn, testBtn;
	private TextView mMsgTxt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process2);
		mProcessTxt = (TextView) findViewById(R.id.process_txt);
		mMsgTxt = (TextView) findViewById(R.id.msg_txt);
		mLinkMainProcessBtn = (Button) findViewById(R.id.link_main_process_btn);
		mProcessTxt.setText("pid = " + android.os.Process.myPid());

		testBtn = (Button) findViewById(R.id.test_btn);
		testBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIMyAidlInterface == null) {
					return;
				}
				new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							Log.e(TAG, "onClick: waitToReady start");
							mIMyAidlInterface.waitToReady();
							Log.e(TAG, "onClick: waitToReady end");
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});

		mLinkMainProcessBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Process2Activity.this, MainService.class);
				bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			}
		});
	}

	IMyAidlInterface mIMyAidlInterface;
	private ServiceConnection mConnection = new ServiceConnection() {
		// Called when the connection with the service is established
		public void onServiceConnected(ComponentName className, IBinder service) {
			// Following the example above for an AIDL interface,
			// this gets an instance of the IRemoteInterface, which we can use to call on the service
			LogUtil.d(TAG, "onServiceConnected");
			mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
			try {
				mMsgTxt.setText("main pic = " + mIMyAidlInterface.getPid());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Called when the connection with the service disconnects unexpectedly
		public void onServiceDisconnected(ComponentName className) {
			LogUtil.e(TAG, "Service has unexpectedly disconnected");
			mIMyAidlInterface = null;
		}
	};

	@Override
	protected void onDestroy() {
		LogUtil.d(TAG, "onDestroy");
		unbindService(mConnection);
		super.onDestroy();
	}
}
