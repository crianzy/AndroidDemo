package com.zuimeia.androidhttpserver1.httpserver.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

public class CoreHttpResponse {
	private String contentType = ContentType.html;
	private HttpResponse mHttpResponse;
	private HashMap<String, String> data = new HashMap<String, String>();
	
	
	public void setHttpHead(int code){
		mHttpResponse.setStatusCode(code);
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public CoreHttpResponse(HttpResponse httpResponse) {
		this.mHttpResponse = httpResponse;
	}
	
	public CoreHttpResponse data(String key,String value){
		data.put(key, value);
		return this;
	}
	
	public void render(String strHtml){
		render(strHtml,null);
	}
	
	
	public void render(String strHtml,String charset){
		if(contentType == ContentType.html && !data.isEmpty()){
			for (Map.Entry<String,String> entry : data.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if(key!=null){
					if(value == null){
						value = "null";
					}
					strHtml = strHtml.replace("{"+key+"}", value);
				}
			}
		}
		
		HttpEntity entity = null;
		try {
			if(charset == null || charset.trim().length() == 0)
				entity = new StringEntity(strHtml,"utf-8");
			else 
				entity = new StringEntity(strHtml, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reander(entity);
	}
	
	public void render(String strHtml,int i){
		render(strHtml,null);
		
	}
	
	public void render(File file){
		if(contentType == ContentType.html && !data.isEmpty()){
			render(readHtml(file));
		}else{
			HttpEntity entity = new FileEntity(file, contentType);
			reander(entity);
		}
	}
	
	
	public void render(final InputStream is){
		if(contentType == ContentType.html && !data.isEmpty()){
			render(readHtml(is));
		}else{
			HttpEntity entity = new EntityTemplate(new ContentProducer() {
				public void writeTo(final OutputStream outstream) throws IOException {
					MultipartStream.copy(is, outstream, false);
				}
			});
			reander(entity);
		}
	}

	
	public void render(Context ctx , String assetPath) {
		InputStream is = null;
		try {
			is = ctx.getAssets().open(assetPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(is != null)
			render(is);
	}
	

	public void reander(HttpEntity entity) {
		mHttpResponse.setHeader("Content-Type", contentType);
		mHttpResponse.setEntity(entity);
	}
	
	public void redirect(String url){
		render(getRedirectContent(url));
	}
	
	private String readHtml(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			return readHtml(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String readHtml(InputStream is){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			MultipartStream.copy(is, baos, true);
			return new String(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getRedirectContent(String url){
		return "<html>" +
				"<head>" +
				"<meta http-equiv=\"Content-Language\" content=\"zh-CN\">" +
				"<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\">" +
				"<meta http-equiv=\"refresh\" content=\"0;url="+url+"\">" +
				"<title>" +
				"</title>" +
				"</head>" +
				"<body></body>" +
				"</html>";
	}
	
}
