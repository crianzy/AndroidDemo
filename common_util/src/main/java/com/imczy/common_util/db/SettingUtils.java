package com.imczy.common_util.db;

import android.content.Context;
import android.text.TextUtils;

import java.util.UUID;

/**
 * Created by chenzhiyong on 15/11/2.
 */
public class SettingUtils {
    private static Context mContext;
    private static SharePrefHelper mLocalHelper;
    private static final String SHARE_PREFERENCE_NAME = "settings.pref";

    public static void init(Context applicationContext) {
        mContext = applicationContext.getApplicationContext();
        mLocalHelper = SharePrefHelper.newInstance(mContext, SHARE_PREFERENCE_NAME);
    }

    public static final String DANMUKU_UUDI = "danmuku_uudi";

    public static void setDanmukuUudi(String uuid) {
        mLocalHelper.setPref(DANMUKU_UUDI, uuid);
    }

    public static UUID getDanmukuUuid() {
        String uuidStr = mLocalHelper.getPref(DANMUKU_UUDI, "");
        if (TextUtils.isEmpty(uuidStr)) {
            UUID uuid = UUID.randomUUID();
            setDanmukuUudi(uuid.toString());
            return uuid;
        } else {
            return UUID.fromString(uuidStr);
        }
    }

    public static String getDanmukuUuidString() {
        return mLocalHelper.getPref(DANMUKU_UUDI, "");
    }
}
