package com.zuimeia.androidhttpserver1.httpserver.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

import com.zuimeia.androidhttpserver1.httpserver.core.ContentType;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpRequest;
import com.zuimeia.androidhttpserver1.httpserver.core.CoreHttpResponse;


public class SmartHandler extends BaseHttpHandler {
	private static final  List<String> indexs= new ArrayList<String>();
	static{
		//{"index.htm","index.html"}
		indexs.add("index.htm");
		indexs.add("index.html");
	}
	private Context mContext;
	private String 	mRootPath;
	private boolean isAssetRoot = false;

	
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public String getmRootPath() {
		return mRootPath;
	}

	public void setmRootPath(String mRootPath) {
		this.mRootPath = mRootPath;
	}

	public boolean isAssetRoot() {
		return isAssetRoot;
	}

	public void setAssetRoot(boolean isAssetRoot) {
		this.isAssetRoot = isAssetRoot;
	}
	
	public void setIndex(String ...index){
		if(index!=null && index.length > 0){
			indexs.clear();
			for(String indexhtml : index){
				indexs.add(indexhtml);
			}
		}
	}


	@Override
	public void handle(CoreHttpRequest request, CoreHttpResponse response) {
		String uri = request.getUri();
		if("/favicon.ico".equalsIgnoreCase(uri)){
			response.render("");
			return;
		}
		
		if(uri.endsWith(".css")){
			response.setContentType(ContentType.css);
		}else if(uri.endsWith(".jpg")){
			response.setContentType(ContentType.jpg);
		}else if(uri.endsWith(".png")){
			response.setContentType(ContentType.png);
		}else if(uri.endsWith(".gif")){
			response.setContentType(ContentType.gif);
		}else if(uri.endsWith(".js")){
			response.setContentType(ContentType.js);
		}else if(uri.endsWith(".html") || uri.endsWith("htm")){
			response.setContentType(ContentType.html);
		}else if(uri.endsWith(".swf")){
			response.setContentType(ContentType.swf);
		}else if(uri.endsWith(".xml")){
			response.setContentType(ContentType.xml);
		}
		
		//其他默认是html
		String path = mRootPath+uri;
		if(isAssetRoot){
			AssetManager asm = mContext.getAssets();
			try {
				InputStream  is = asm.open(path);
				response.render(is);
			} catch (IOException e) { //maybe path is directory
				for(String index : indexs){
					String indexPath = path+index;
					try {
						InputStream is = asm.open(indexPath);
						response.render(is);
						break;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}else{
			File file = new File(path);
			if(file.isDirectory()){
				for(String index : indexs){
					File indexHtmlFile = new File(file, index);
					if(indexHtmlFile.exists()){
						response.render(indexHtmlFile);
						break;
					}
				}
			}else{
				response.render(new File(mRootPath + uri));
			}
		}
	}

}
