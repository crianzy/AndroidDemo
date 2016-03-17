package com.zuimeia.androidhttpserver1.httpserver.handler;

import com.zuimeia.androidhttpserver1.httpserver.core.ContentType;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpRequest;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpResponse;
import com.zuimeia.androidhttpserver1.httpserver.core.MultipartStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;


public abstract class BaseHttpHandler implements HttpRequestHandler,ContentType {
	private static final String multipart = "multipart";
	
	
	@Override
	public void handle(HttpRequest request, HttpResponse resp, HttpContext context) throws HttpException, IOException {
		try {
			String method = request.getRequestLine().getMethod();
			CoreHttpRequest tzRequest = new CoreHttpRequest(request);
			CoreHttpResponse tzResponse = new CoreHttpResponse(resp);
			init(tzRequest, tzResponse);
			if("GET".equalsIgnoreCase(method)) //do get
			{
				tzRequest.setMethod("GET");
				tzRequest.fillParams(URI.create(request.getRequestLine().getUri()));
				handle(tzRequest, tzResponse);
			}
			else if("POST".equalsIgnoreCase(method))//do post
			{ 
				tzRequest.setMethod("POST");
				HttpEntity entity = null;
				if(request instanceof BasicHttpEntityEnclosingRequest){
					BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
					entity = req.getEntity();
				}else if(request instanceof EntityEnclosingRequestWrapper){
					BasicHttpEntityEnclosingRequest req = (BasicHttpEntityEnclosingRequest) request;
					entity = req.getEntity();
				}else if(request instanceof HttpEntityEnclosingRequest){
					HttpEntityEnclosingRequest req = (HttpEntityEnclosingRequest) request;
					entity = req.getEntity();
				}
				
				if(entity!=null){
					String value = entity.getContentType().getValue();
					if(value!=null && value.startsWith(multipart)){
						doHandleFileUpload(tzRequest,tzResponse,entity);
					}else{
						tzRequest.fillParams(entity);	
						handle(tzRequest, tzResponse);
					}
				}
			}
			else //other method
			{
				tzRequest.setMethod("UNKNOW");
				handle(tzRequest, tzResponse);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void doHandleFileUpload(CoreHttpRequest tzRequest,CoreHttpResponse tzResponse,HttpEntity entity){
		try {
			InputStream is = entity.getContent();
			String contentType = entity.getContentType().toString();
			int boundaryIndex = contentType.indexOf("boundary=");
			if( boundaryIndex >= 0 ){
				byte[] boundary = (contentType.substring(boundaryIndex + 9)).getBytes();
				MultipartStream multipartStream = new MultipartStream(is, boundary);
				
				boolean hasNextPart = multipartStream.skipPreamble();
				while(hasNextPart) {
					
				  String headerInfo = multipartStream.readHeaders();
				 
				  boolean isFileUpload = headerInfo!=null && headerInfo.indexOf("filename=")!= -1 ;
//						  && headerInfo.indexOf("filename=\"") < headerInfo.indexOf("Content-Type:")
//						  && headerInfo.indexOf("Content-Type:") < headerInfo.indexOf("application/");
				  
				  OutputStream os = null;
				  if(isFileUpload){
					  os = handleFileUpload(headerInfo);
				  }else{
					  os = new ByteArrayOutputStream();
				  }
						  
				  if(os !=null){
					  multipartStream.readBodyData(os);
				  }
				  
				  if(!isFileUpload){
					  ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
					  baos.flush();
					  baos.close();
					  int index1 = headerInfo.indexOf("name=\"")+6;
					  int index2 = headerInfo.indexOf("\"", index1);
					  String key = headerInfo.substring(index1, index2);
					  String content = new String(baos.toByteArray());
					  tzRequest.fillParams(key,content);
				  }
				  
				  hasNextPart = multipartStream.readBoundary();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			handle(tzRequest, tzResponse);
		}
	}
	
	public void init(CoreHttpRequest request,CoreHttpResponse response){};
	public abstract void handle(CoreHttpRequest request,CoreHttpResponse response);
	public OutputStream handleFileUpload(String headerInfo){
		return null;
	}

}


