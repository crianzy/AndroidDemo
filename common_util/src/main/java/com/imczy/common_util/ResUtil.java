package com.imczy.common_util;

import android.content.Context;
import android.content.res.Resources;

public class ResUtil {
	private Resources resources;
	private String pkg;
	private static ResUtil ru;

	private ResUtil(Context context) {
		pkg = context.getPackageName();
		resources = context.getResources();
	}

	public static ResUtil getInstance(Context context) {
		if (ru == null)
			ru = new ResUtil(context);
		return ru;
	}

	protected int resourcesId(Context context, String type, String name) {
		try {
			int id = resources.getIdentifier(name, type, pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int stringId(String name) {
		try {
			int id = resources.getIdentifier(name, "string", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int menuId(String name) {
		try {
			int id = resources.getIdentifier(name, "menu", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int colorId(String name) {
		try {
			int id = resources.getIdentifier(name, "color", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int dimenId(String name) {
		try {
			int id = resources.getIdentifier(name, "dimen", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int styleId(String name) {
		try {
			int id = resources.getIdentifier(name, "style", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int layoutId(String name) {
		try {
			int id = resources.getIdentifier(name, "layout", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int viewId(String name) {
		try {
			int id = resources.getIdentifier(name, "id", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int animId(String name) {
		try {
			int id = resources.getIdentifier(name, "anim", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int arrayId(String name) {
		try {
			int id = resources.getIdentifier(name, "array", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int drawableId(String name) {
		try {
			int id = resources.getIdentifier(name, "drawable", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int rawId(String name) {
		try {
			int id = resources.getIdentifier(name, "raw", pkg);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
