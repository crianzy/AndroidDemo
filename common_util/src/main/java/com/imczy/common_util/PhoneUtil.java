package com.imczy.common_util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;

public class PhoneUtil {

    public static String getUserAgent() {
        return getPhoneType();
    }


    /**
     * 获取手机IMEI码
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null)
            imei = "";
        return imei;
    }

    /**
     * 获取手机IMSI码
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (imsi == null)
            imsi = "";
        return imsi;
    }

    /**
     * 获取手机网络型号
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取手机机型:i9250
     *
     * @return
     */
    public static String getPhoneType() {
        String type = Build.MODEL;
        if (type != null) {
            type = type.replace(" ", "");
        }
        return type.trim();
    }

    public static String getDevice() {
        return Build.DEVICE;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static String getType() {
        return Build.TYPE;
    }

    /**
     * 获取手机操作系统版本名：如2.3.1
     *
     * @return
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机操作系统版本号：如4
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * 获取手机操作系统版本号：如4
     *
     * @return
     */
    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String nativePhoneNumber = telephonyManager.getLine1Number();
        if (nativePhoneNumber == null) {
            nativePhoneNumber = "";
        }
        return nativePhoneNumber;
    }

    /**
     * 获取屏幕尺寸，如:320x480
     *
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels + "x" + dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int displayH = getDisplayHeight2(context);
        // w:1280 h:720 ->w=h
        // w:720 h:1280 ->ok
        return dm.widthPixels < displayH ? dm.widthPixels : displayH;
    }

    public static int getDisplayWidth2(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    // public static int getDisplayHeight(Context context) {
    // DisplayMetrics dm = new DisplayMetrics();
    // WindowManager wm = (WindowManager)
    // context.getSystemService(Context.WINDOW_SERVICE);
    // wm.getDefaultDisplay().getMetrics(dm);
    // return dm.heightPixels;
    // }
    public static int getDisplayHeight2(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);
        if (Build.VERSION.SDK_INT < 13) {
            return dm.heightPixels;
        } else if (Build.VERSION.SDK_INT == 13) {
            try {
                Method mt = display.getClass().getMethod("getRealHeight");
                return (Integer) mt.invoke(display);
            } catch (Exception e) {
            }
        } else if (Build.VERSION.SDK_INT > 13 && Build.VERSION.SDK_INT < 17) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                return (Integer) mt.invoke(display);
            } catch (Exception e) {
            }
        } else if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            return dm.heightPixels;
        }
        return dm.heightPixels;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);
        int displayW = getDisplayWidth2(context);
        if (Build.VERSION.SDK_INT < 13) {
            // w:1280 h:720 ->h=w
            // w:720 h:1280 ->ok
            return dm.heightPixels > displayW ? dm.heightPixels : displayW;
        } else if (Build.VERSION.SDK_INT == 13) {
            try {
                Method mt = display.getClass().getMethod("getRealHeight");
                int displayH = (Integer) mt.invoke(display);
                return displayH > displayW ? displayH : displayW;
            } catch (Exception e) {
            }
        } else if (Build.VERSION.SDK_INT > 13 && Build.VERSION.SDK_INT < 17) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                int displayH = (Integer) mt.invoke(display);
                return displayH > displayW ? displayH : displayW;
            } catch (Exception e) {
            }
        } else if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            return dm.heightPixels > displayW ? dm.heightPixels : displayW;
        }
        return dm.heightPixels > displayW ? dm.heightPixels : displayW;
    }

    public static int getScreentHeight(Context context) {
        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getDisplayVisibleHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        Drawable phoneCallIcon = context.getResources().getDrawable(android.R.drawable.stat_sys_phone_call);
        int barheight = phoneCallIcon.getIntrinsicHeight();

        return dm.heightPixels - barheight;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static float getDisplayDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static float getDisplayDensityDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * 获取手机MAC地址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null)
            return "";
        return info.getMacAddress();
    }

    /**
     * 获取基带版本
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String getBaseand() {
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            return result.toString();
        } catch (Exception e) {
        }
        return "";
    }

    public static int getCacheSize(Context context) {
        return 1024 * 1024 * ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    }

    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
     *
     * @param context
     * @param unit
     * @param size
     * @return
     */
    public static int getRawSize(Context context, int unit, float size) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return (int) TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    static public String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }

        String strInfo = "";
        RandomAccessFile reader = null;
        try {
            byte[] bs = new byte[1024];
            reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strInfo;
    }

    static public String getCpuType() {
        String strInfo = getCpuString();
        String strType = null;

        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }

        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }

        return strType;
    }

    /**
     * @param context
     * @return 判断是否是lpad, px=dp*(dpi/160)
     */
    // public static boolean isPad(Context context) {
    // int screenWidth = getDisplayWidth(context);
    // float density = getDisplayDensity(context);
    // int dp = (int) (screenWidth / density);
    // if (dp >= 600) {
    // return true;
    // }
    // return false;
    // }
    public static boolean isPad(Context context) {
        boolean isPad = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (!isPad) {// 不是pad
            return false;
        }
        float dp = getDisplayWidth(context) / getDisplayDensity(context);
        return (dp >= 600f ? true : false);
    }

    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
                if (typeName.equals("wifi")) {
                    typeName = "wifi";
                } else {
                    typeName = info.getExtraInfo().toLowerCase(); // 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
                }
                return typeName;
            } else {
                return "noNetwork";
            }
        } catch (Exception e) {

        }
        return "noNetwork";
    }

    public static boolean isAndroidL() {
        if (Build.VERSION.SDK_INT == 20) {
            return true;
        } else {
            return false;
        }
    }

    private static final int NETWORK_TYPE_EHRPD = 14; // Level 11
    private static final int NETWORK_TYPE_EVDO_B = 12; // Level 9
    private static final int NETWORK_TYPE_HSPAP = 15; // Level 13
    private static final int NETWORK_TYPE_IDEN = 11; // Level 8
    private static final int NETWORK_TYPE_LTE = 13; // Level 11

    public static String getSubtype(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return "50-100 kbps"; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return "14-64 kbps"; // ~ 14-64 kbps
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return "50-100 kbps"; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return "400-1000 kbps"; // ~ 400-1000 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return "600-1400 kbps"; // ~ 600-1400 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return "100 kbps"; // ~ 100 kbps
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return "2-14 Mbps"; // ~ 2-14 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return "700-1700 kbps"; // ~ 700-1700 kbps
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return "1-23 Mbps"; // ~ 1-23 Mbps
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return "400-7000 kbps"; // ~ 400-7000 kbps
                    case NETWORK_TYPE_EHRPD:
                        return "1-2 Mbps"; // ~ 1-2 Mbps
                    case NETWORK_TYPE_EVDO_B:
                        return "5 Mbps"; // ~ 5 Mbps
                    case NETWORK_TYPE_HSPAP:
                        return "10-20 Mbps"; // ~ 10-20 Mbps
                    case NETWORK_TYPE_IDEN:
                        return "25 kbps"; // ~25 kbps
                    case NETWORK_TYPE_LTE:
                        return "10+ Mbps"; // ~ 10+ Mbps
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        return "unknown";
                }
            } else {
                return "unknown";
            }
        }
        return "no net";
    }

    /**
     * 获取手机语言
     *
     * @return
     */
    public static String getPhoneLanguage() {
        try {
            Locale locale = Locale.getDefault();
            String param = locale.getLanguage() + "-" + locale.getCountry();
            return param.toLowerCase();
        } catch (Throwable t) {
        }

        return "";
    }

    /**
     * 获取状态栏高度
     *
     * @return
     * @hide
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static void expandNotification(Context context) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            Method method = null;
            if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT) {
                method = clazz.getMethod("expandNotificationsPanel");
            } else if (Build.VERSION_CODES.ICE_CREAM_SANDWICH <= Build.VERSION.SDK_INT
                    && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                method = clazz.getMethod("expand");
            }
            method.setAccessible(true);
            method.invoke(service);
        } catch (Exception e) {
        }
    }

    public static void collapseNotification(Context context) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            Method method = null;
            if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT) {
                method = clazz.getMethod("collapsePanels");
            } else if (Build.VERSION_CODES.ICE_CREAM_SANDWICH <= Build.VERSION.SDK_INT
                    && Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                method = clazz.getMethod("collapse");
            }
            method.setAccessible(true);
            method.invoke(service);
        } catch (Exception e) {
        }
    }

    public static int getCurrTimeZone() {
        return TimeZone.getDefault().getRawOffset() / 60 / 60 / 1000;
    }

    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }
}
