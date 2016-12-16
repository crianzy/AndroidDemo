package com.czy.hotfixdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dodola.rocoofix.RocooFix;
import com.imczy.common_util.io.IOUtil;

import java.io.File;

/**
 * Created by chenzhiyong on 16/8/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //打补丁
        RocooFix.init(this);

//        findPatch();
    }

    private void findPatch() {
        File patchDir = new File(IOUtil.getBaseLocalLocation(this) + File.separator + "zzz_test");
        if (!patchDir.exists()) {
            boolean r = patchDir.mkdirs();
            Log.e(TAG, "findPatch: mkdirs result = " + r);
        }

        File patchFile = new File(patchDir.getAbsoluteFile() + File.separator + "patch.jar");
        Log.e(TAG, "findPatch: patchFile.exists() = " + patchFile.exists());
        if (!patchFile.exists()) {
            return;
        }
        RocooFix.applyPatch(this, patchFile.getAbsolutePath());
    }
}
