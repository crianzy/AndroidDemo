package v7.support.android.share;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    shareFacebook("asdasd https://www.baidu.com/");
                    whatsapp("asdasd https://www.baidu.com/");
                } catch (Exception e) {
                    e.printStackTrace();
                    //未安装应用，可以调用第三方或者提示用户未安装应用
                    ShareUtil.shareLink(getContext(), ShareUtil.SHARE_WAY_FACEBOOK, "asdasd https://www.baidu.com/");
                }
            }
        });
    }

    public Context getContext() {
        return this;
    }


    public void shareFacebook(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.facebook.katana", "com.facebook.composer.shareintent.ImplicitShareIntentHandlerDefaultAlias");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    public void shareTwitter(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    // START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.google.android.apps.plus/com.google.android.libraries.social.gateway.GatewayActivity
    public void googlePlus(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.google.android.apps.plus", "com.google.android.libraries.social.gateway.GatewayActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    //START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.facebook.orca/com.facebook.messenger.intents.ShareIntentHandler
    public void facebookMessenger(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.facebook.orca", "com.facebook.messenger.intents.ShareIntentHandler");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    //  START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.snapchat.android/.LandingPageActivity c
    public void snapchat(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.snapchat.android", "com.snapchat.android.LandingPageActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    //Imo START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.imo.android.imoim/.activities.SharingActivity
    public void imo(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.imo.android.imoim", "com.imo.android.imoim.activities.SharingActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }


    //START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=jp.naver.line.android/.activity.selectchat.SelectChatActivity
    public void line(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("jp.naver.line.android", "jp.naver.line.android.activity.selectchat.SelectChatActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }


    // START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.bbm/.ui.share.SingleEntryShareActivity

    public void bbm(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.bbm", "com.bbm.ui.share.SingleEntryShareActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    //yeecall、instagram


    //  START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.google.android.apps.messaging/.ui.conversationlist.ShareIntentActivity
    // 短信

    public void duanxin(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.google.android.apps.messaging", "com.google.android.apps.messaging.ui.conversationlist.ShareIntentActivity");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    //START u0 {act=android.intent.action.SEND typ=text/plain flg=0xb080001 cmp=com.whatsapp/.ContactPicker

    public void whatsapp(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.whatsapp", "com.whatsapp.ContactPicker");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

}
