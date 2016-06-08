package andoridhost.imczy.com.abortbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by chenzy on 2015/4/11.
 */
public class PackageInstallOrRemoveReceiver extends BroadcastReceiver {
    private static final String TAG = "PackageInstallOrRemoveReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收广播：设备上新安装了一个应用程序包后自动启动新安装应用程序。
        Log.e(TAG, "onReceive: intent = " + intent);
        abortBroadcast();
    }

}
