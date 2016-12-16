package com.czy.databinding;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by chenzhiyong on 16/7/22.
 */
public class MyHandlers {
    private static final String TAG = "MyHandlers";

    public void onClickFriend(View view) {
        Log.w(TAG, "onClickFriend() called with: " + "view = [" + view + "]");
        Toast.makeText(view.getContext(), "click", Toast.LENGTH_SHORT).show();

    }
}
