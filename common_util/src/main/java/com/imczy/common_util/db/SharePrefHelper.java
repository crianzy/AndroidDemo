/**
 * Description:   SharePreference的工具类
 *	
 * Author：       Yu Jingye
 * Create Date：  2013-01-21
 * Version:       1.0
 */
package com.imczy.common_util.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefHelper {

	private SharedPreferences prefs = null;
	private static SharePrefHelper sharePrefHelper = null;
	private final String PREFS_FILE = "brixd.prefs";
	
	protected SharePrefHelper(Context cxt) {
//		Context context;
//		try {
//			context = cxt.createPackageContext(cxt.getPackageName(), Context.CONTEXT_IGNORE_SECURITY);
//			prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}

		prefs = cxt.getSharedPreferences(PREFS_FILE, Context.MODE_MULTI_PROCESS);
	}

	protected SharePrefHelper(Context cxt, String name) {
		prefs = cxt.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public static SharePrefHelper getInstance(Context context) {
		if (sharePrefHelper == null) {
			sharePrefHelper = new SharePrefHelper(context);
		}
		return sharePrefHelper;
	}

	public static SharePrefHelper newInstance(Context context) {
		sharePrefHelper = new SharePrefHelper(context);
        return sharePrefHelper;
	}

	/**
	 * Return new instance of SharePrefHelper, won't change it's static instance.
	 * @param context
	 * @param name
	 * @return
	 */
	public static SharePrefHelper newInstance(Context context, String name) {
		return new SharePrefHelper(context, name);
	}

	public void setPref(String key, boolean value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void setPref(String key, String value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void setPref(String key, float value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public void setPref(String key, int value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setPref(String key, long value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public boolean getPref(String key, Boolean defaultValue) {
		return prefs.getBoolean(key, defaultValue);
	}
	
	public String getPref(String key, String defaultValue) {
		return prefs.getString(key, defaultValue);
	}
	
	public int getPref(String key, int defaultValue) {
		return prefs.getInt(key, defaultValue);
	}
	
	public long getPref(String key, long defaultValue) {
		return prefs.getLong(key, defaultValue);
	}
	
	public float getPref(String key, float defaultValue) {
		return prefs.getFloat(key, defaultValue);
	}

	public boolean hasPrefWithKey(String key) {
		return prefs.contains(key);
	}
	
	public boolean removePref(String key) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(key);
		return editor.commit();
	}
}
