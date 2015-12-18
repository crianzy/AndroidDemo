package com.czy.textviewdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class TypefaceUtil {
	
	private static final Map<String, Typeface> cache = new HashMap<String, Typeface>();

	private static Typeface get(Context c, String path){
		synchronized(cache){
			String key = c.getPackageName() + ":" + path;
			if(!cache.containsKey(path)){
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(), path);
					cache.put(key, t);
				} catch (Exception e) {
				}
			}
			return cache.get(key);
		}
	}

	public static Typeface getTypeface(Context context, String path) {
		return get(context, path);
	}

	public static Typeface getTypeface(Context c, String path, String targetPackage) {
		Context targetContext = null;
		if (TextUtils.isEmpty(targetPackage) || c.getPackageName().equals(targetPackage)) {
			targetContext = c;
		} else {
			try {
				targetContext = c.createPackageContext(targetPackage, Context.CONTEXT_IGNORE_SECURITY);
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}

		if (targetContext != null) {
			return get(targetContext, path);
		}

		return null;
	}
}
