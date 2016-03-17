package com.zuimeia.androidhttpserver1.httpserver.handler;

import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpRequest;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpResponse;

import java.io.File;

public class FileHandler extends BaseHttpHandler{
	
	private File file;
	
	public FileHandler(String path) {
		this.file = new File(path);
	}
	
	public FileHandler(File file) {
		this.file = file;
	}
	

	@Override
	public void handle(CoreHttpRequest request, CoreHttpResponse response) {
		response.render(file);
	}

}
