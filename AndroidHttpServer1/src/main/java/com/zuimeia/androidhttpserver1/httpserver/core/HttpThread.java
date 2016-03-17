package com.zuimeia.androidhttpserver1.httpserver.core;

import com.zuimeia.androidhttpserver1.httpserver.utils.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

/**
 * 
 * @title http处理线程
 * @company 北京奔流网络信息技术有限公司
 * @email mail@wangdingchun.net
 * @author Vermouth
 * @version 1.0
 * @created 2015-2-27 下午1:09:20
 * @changeRecord [修改记录]<br />
 */
public class HttpThread extends Thread {
	
	private HttpService httpService = null;
	private Socket soket = null;
	
	public HttpThread(Socket soket) {
		this.soket = soket;
		
		BasicHttpProcessor httpProcessor = new BasicHttpProcessor();
		httpProcessor.addInterceptor(new ResponseDate());
	    httpProcessor.addInterceptor(new ResponseServer());
	    httpProcessor.addInterceptor(new ResponseContent());
	    httpProcessor.addInterceptor(new ResponseConnControl());
	    
	    httpService = new HttpService(httpProcessor, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
	}
	
	/*
     * 路由配置
     */
	public void configHandler(Map<String, HttpRequestHandler> handlers){
		if(handlers!=null && handlers.size() > 0){
			 HttpRequestHandlerRegistry HttpRHR = new HttpRequestHandlerRegistry();
			for(Map.Entry<String, HttpRequestHandler> entry :  handlers.entrySet()){
				HttpRHR.register(entry.getKey(), entry.getValue());
			}
			httpService.setHandlerResolver(HttpRHR);//设置Http处理解析器
		}
	}
	
	

	public void run(){
		DefaultHttpServerConnection httpserver = new DefaultHttpServerConnection();
		try {
			httpserver.bind(this.soket, new BasicHttpParams());
			
			BasicHttpContext httpContext = new BasicHttpContext();
			httpService.handleRequest(httpserver, httpContext);
		} catch (Exception exce){
			if(Log.DEBUG)
				exce.printStackTrace();
		}finally {
			try {
				httpserver.close();
			} catch (IOException e) {
				if(Log.DEBUG)
					e.printStackTrace();
			}
		}
	}
	
}
