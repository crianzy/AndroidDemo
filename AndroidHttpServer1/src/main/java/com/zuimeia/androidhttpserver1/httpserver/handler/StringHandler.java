package com.zuimeia.androidhttpserver1.httpserver.handler;


import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpRequest;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpResponse;

public class StringHandler extends BaseHttpHandler{
	
	private String text;
	private String charset;
	
	
	public StringHandler(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public StringHandler text(String text) {
		this.text = text;
		return this;
	}
	
	
	public String getCharset() {
		return charset;
	}

	public StringHandler charset(String charset) {
		this.charset = charset;
		return this;
	}


	@Override
	public void handle(CoreHttpRequest request, CoreHttpResponse response) {
		response.render(text);
	}


}
