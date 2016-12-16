package v7.support.android.share;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by chenzhiyong on 2016/11/27.
 */

public class ShareUtil {

    public static String SHARE_WAY_INSTAGRAM = "com.instagram.android";
    public static String SHARE_WAY_WHATSAPP = "com.whatsapp";
    public static String SHARE_WAY_MESSENGER = "com.facebook.orca";
    public static String SHARE_WAY_TWITTER = "com.twitter.android";
    public static String SHARE_WAY_FACEBOOK = "com.facebook.katana";


    public static void shareLink(Context context, String shareWay, @NonNull String shareCaption) {
        // 判断软件是否安装
        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType("text/plain");
        // Set the packageName
        if (!TextUtils.isEmpty(shareWay)) {
            share.setPackage(shareWay);
        }

        if (!TextUtils.isEmpty(shareCaption)) {
            share.putExtra(Intent.EXTRA_TEXT, shareCaption);
        }
        // Broadcast the Intent.
        context.startActivity(Intent.createChooser(share, "share"));
    }
}
