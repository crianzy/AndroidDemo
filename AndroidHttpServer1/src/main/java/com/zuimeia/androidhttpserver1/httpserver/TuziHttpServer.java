package com.zuimeia.androidhttpserver1.httpserver;

import com.zuimeia.androidhttpserver1.httpserver.core.HttpThread;
import com.zuimeia.androidhttpserver1.httpserver.utils.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.protocol.HttpRequestHandler;


/**
 * 
 * @title Http服务器
 * @description Http服务器处理线程
 * @company 北京奔流网络信息技术有限公司
 * @email mail@wangdingchun.net
 * @author Vermouth
 * @version 1.0
 * @created 2015-2-27 下午1:01:55
 * @changeRecord [修改记录]<br />
 */
public class TuziHttpServer extends Thread{
	private static final String TAG = TuziHttpServer.class.getSimpleName();
	
	private final int port ;
	private String portString;
	public String getPortString() {
		return portString;
	}
	private boolean isRunning = false;
	private Map<String, HttpRequestHandler> handlerMap = new ConcurrentHashMap<String, HttpRequestHandler>();
	
	public StartCompleteListener startCompleteListener;
	
	
	public void setStartCompleteListener(StartCompleteListener startCompleteListener) {
		this.startCompleteListener = startCompleteListener;
	}

	public TuziHttpServer(int port){
		Log.d("Tuzi3Service", "mHttpServer is init");
		this.port = port;
		this.setName("TuziHttpServer");
	}
	
	public void addHandler(String regex , HttpRequestHandler handler){
		handlerMap.put(regex, handler);
	}
	
	@Override
	public void run(){
		super.run();
		Log.d("Tuzi3Service", "mHttpServer is starting");
		ServerSocket server = null;
		Socket soket = null;
		try {
			try {
				server = new ServerSocket(port);
			} catch (Exception e) {
				server=new ServerSocket(0);
			}
			Log.d("Tuzi3Service", "mHttpServer is already start");
			server.setReuseAddress(true);
			server.setSoTimeout(5000);
			isRunning = true;
			if(startCompleteListener!=null){
				startCompleteListener.getSocketInfo(server.getInetAddress()+"", server.getLocalPort()+"");
			}
			while (isRunning) {
				try {
					soket = server.accept();
					portString=server.getLocalPort()+"";
					HttpThread httpThread = new HttpThread(soket);
					httpThread.configHandler(handlerMap);
					httpThread.setDaemon(true);
					httpThread.start();
		        } catch (Exception e) {
                	if(Log.DEBUG)
                		e.printStackTrace();
                }
			}	
	    } catch (IOException e) {
			e.printStackTrace();
	    	Log.e(TAG, e.getMessage());
		}finally{
			isRunning=false;
			try{
		    	if(soket != null) 
		    		soket.close();
		    	if(server != null)
		    		server.close();
	    	} catch(Exception ex){
	    		if(Log.DEBUG)
            		ex.printStackTrace();
	    	}
		}
	}
	
	public void stopServer(){
		isRunning = false;
	}
	
	public boolean isRunning(){
		return isRunning; 
	}
	public static interface StartCompleteListener{
		public void getSocketInfo(String ip, String port);
	}
}