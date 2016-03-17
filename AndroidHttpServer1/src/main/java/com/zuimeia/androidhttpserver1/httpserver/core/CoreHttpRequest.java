package com.zuimeia.androidhttpserver1.httpserver.core;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class CoreHttpRequest {
	private HttpRequest mHttpRequest;
	private HashMap<String, String> mParams;
	private String mMethod;
	
	public CoreHttpRequest(CoreHttpRequest httpRequest) {
		this.mHttpRequest = httpRequest.mHttpRequest;
		this.mParams = new HashMap<String, String>();
		this.mParams.putAll(httpRequest.mParams);
		this.mMethod = httpRequest.mMethod;
	}
	
	public CoreHttpRequest(HttpRequest httpRequest) {
		this.mHttpRequest = httpRequest;
		this.mParams = new HashMap<String, String>();
	}
	
	
	public void fillParams(HttpEntity entity){
		if(entity == null) return;
		
		List<NameValuePair> nvpList= parse(entity,HTTP.UTF_8);
		if(nvpList!=null && nvpList.size() > 0){
			for(NameValuePair nvp : nvpList){
				mParams.put(nvp.getName(), nvp.getValue());
			}
		}
	}
	
	public void fillParams(URI uri){
		if(uri == null) return;
		
		List<NameValuePair> nvpList= URLEncodedUtils.parse(uri,HTTP.UTF_8);
		if(nvpList!=null && nvpList.size() > 0){
			for(NameValuePair nvp : nvpList){
				mParams.put(nvp.getName(), nvp.getValue());
			}
		}
	}
	
	public void fillParams(String content){
		if(content == null) return;
		
		List<NameValuePair> nvpList= parse(content,HTTP.UTF_8);
		if(nvpList!=null && nvpList.size() > 0){
			for(NameValuePair nvp : nvpList){
				mParams.put(nvp.getName(), nvp.getValue());
			}
		}
	}
	
	public void fillParams(String key ,String value){
		if(key == null) return;
		mParams.put(key, value);
	}
	public String getParam(String key){
		return mParams.get(key);
	}
	
	public HashMap<String, String> getParams(){
		return mParams;
	}


	public String getMethod() {
		return mMethod;
	}


	public void setMethod(String method) {
		this.mMethod = method;
	}
	
	
	public String getUri(){
		return mHttpRequest.getRequestLine().getUri();
	}
	
	private static List <NameValuePair> parse (final HttpEntity entity,final String encoding) {
        List <NameValuePair> result = Collections.emptyList();
        if (URLEncodedUtils.isEncoded(entity)) {
            String content = null;
			try {
				content = EntityUtils.toString(entity);
			} catch (IOException e) {}
            if (content != null && content.length() > 0) {
                result = new ArrayList <NameValuePair>();
                URLEncodedUtils.parse(result, new Scanner(content), 
                        encoding != null ? encoding : null);
            }
        }
        return result;
	}
	
	private static List <NameValuePair> parse (final String content,final String encoding) {
        List <NameValuePair> result = Collections.emptyList();
        if (content != null && content.length() > 0) {
            result = new ArrayList <NameValuePair>();
            URLEncodedUtils.parse(result, new Scanner(content), 
                    encoding != null ? encoding : null);
        }
        return result;
	}
	
	
	public HttpRequest getHttpRequest() {
		return mHttpRequest;
	}
	
	public boolean isFromPhone(){
		String ua = null;
		HeaderIterator ht = mHttpRequest.headerIterator();
		while(ht.hasNext()){
			Header header =  ht.nextHeader();
			String name = header.getName();
			if("User-Agent".equalsIgnoreCase(name)){
				ua = header.getValue();
				break;
			}
		}
		if(ua!=null)
			return HttpRequestDeviceUtils.isMobileDevice(ua);
		return false;
	}
}
