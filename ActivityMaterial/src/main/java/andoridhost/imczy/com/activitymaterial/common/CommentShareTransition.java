package andoridhost.imczy.com.activitymaterial.common;

import android.content.Context;
import android.transition.TransitionSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/6/6.
 */
public class CommentShareTransition extends TransitionSet {

    private static final String TAG = "CommentBottomBarEnterTransition";


    private static final String PROPNAME_TRANSITION_Y = "customtransition:change_transY:transitionY";


    public CommentShareTransition(Context context, View topHeaderView, View BottomView) {
        addTransition(new CommentBottomBarEnterTransition(BottomView));
        addTransition(new CommentTopBarEnterTransition(topHeaderView, context));
    }
}
