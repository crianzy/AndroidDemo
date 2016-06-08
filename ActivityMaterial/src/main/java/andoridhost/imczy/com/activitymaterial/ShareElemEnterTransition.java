package andoridhost.imczy.com.activitymaterial;

import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.util.Log;

/**
 * Created by chenzhiyong on 16/6/7.
 */
public class ShareElemEnterTransition extends TransitionSet{
    private static final String TAG = "ShareElemEnterTransition";

    public ShareElemEnterTransition() {
        Log.d(TAG, "ShareElemEnterTransition() called with: " + "");
//        addTransition(new RevealTransition());
        addTransition(new ChangeBounds());
    }



}
