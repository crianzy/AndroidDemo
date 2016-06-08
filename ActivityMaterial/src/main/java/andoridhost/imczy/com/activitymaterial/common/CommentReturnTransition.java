package andoridhost.imczy.com.activitymaterial.common;

import android.content.Context;
import android.transition.TransitionSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/6/6.
 */
public class CommentReturnTransition extends TransitionSet {

    public CommentReturnTransition(Context context, View topHeaderView, View BottomView) {
        addTransition(new CommentTopBarReturnTransition(topHeaderView, context));

        addTransition(new CommentBottomBarReturnTransition(BottomView));
    }
}
