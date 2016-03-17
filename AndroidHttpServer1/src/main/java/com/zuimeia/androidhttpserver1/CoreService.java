package com.zuimeia.androidhttpserver1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.zuimeia.androidhttpserver1.httpserver.TuziHttpServer;
import com.zuimeia.androidhttpserver1.httpserver.handler.SmartHandler;
import com.zuimeia.androidhttpserver1.httpserver.utils.Log;


public class CoreService extends Service {

	public static final String TAG = CoreService.class.getSimpleName();
	private static final String COMMAND_KEY = "_command"; // 启动该服务的时候传输的一些命令

	public static final int COMMAND_START_HTTP_SERVER = 1;// 启动HTTP服务器
	public static final int COMMAND_STOP_HTTP_SERVER = 2;// 停止HTTP服务器

	public static void doStartHttpServer(Context ctx) {
		Intent baseIntent = new Intent();
		start(baseIntent, ctx, COMMAND_START_HTTP_SERVER);
	}

	public static void doStopHttpServer(Context ctx) {
		Intent baseIntent = new Intent();
		start(baseIntent, ctx, COMMAND_STOP_HTTP_SERVER);
	}

	/**
	 * 停止服务
	 * 
	 * @param ctx
	 */
	public static void stopService(Context ctx) {
		Intent serviceIntent = new Intent();
		serviceIntent.setClass(ctx, CoreService.class);
		ctx.stopService(serviceIntent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * 启动任务
	 * 
	 * @param ctx
	 * @param command
	 */
	private static void start(Intent baseIntent, Context ctx, int command) {
		Intent serviceIntent = new Intent(baseIntent);
		serviceIntent.setClass(ctx, CoreService.class);
		serviceIntent.putExtra(COMMAND_KEY, command);
		ctx.startService(serviceIntent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			int command = intent.getIntExtra(COMMAND_KEY, 0);

			switch (command) {
			case COMMAND_START_HTTP_SERVER:
				exeStartHttpServer();
				break;
			case COMMAND_STOP_HTTP_SERVER:
				exeStopHttpServer();
				break;
			default:
				break;
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	TuziHttpServer mHttpServer;

	private void exeStartHttpServer() {
		Log.d(TAG, "mHttpServer ready to start");
		if (mHttpServer != null && mHttpServer.isRunning()) {
			return;
		}

		int port = 12345;// 设置启动端口 0：代表随机启动端口
		mHttpServer = new TuziHttpServer(port);
		SmartHandler smartHandler = new SmartHandler();
		smartHandler.setAssetRoot(true);// 设置从assert目录下读取
		smartHandler.setmContext(this);// 设置上下文

		smartHandler.setmRootPath("html");// 配置smartHandler的识别网页的路径
		mHttpServer.addHandler("*", smartHandler);// 配置路由分发，必须加smartHandler；用于自动匹配
		//根据业务去配置请求路径
		mHttpServer.addHandler("/index", new ResultHandler(this));//比如请求 http://127.0.0.1:12345/index 就会在resultHandler上处理；
		
		
		mHttpServer.start();
		mHttpServer.setStartCompleteListener(new TuziHttpServer.StartCompleteListener() {

			@Override
			public void getSocketInfo(String ip, final String port) {
				Log.d(TAG, ip + " " + port); // 获取监听的端口地址
			}
		});
	}

	private void exeStopHttpServer() {
		if (mHttpServer != null)
			mHttpServer.stopServer();
	}

}
