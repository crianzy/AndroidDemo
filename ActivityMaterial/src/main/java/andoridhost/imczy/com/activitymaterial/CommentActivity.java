package andoridhost.imczy.com.activitymaterial;

import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;

import andoridhost.imczy.com.activitymaterial.common.CommentEnterTransition;
import andoridhost.imczy.com.activitymaterial.common.CommentReturnTransition;
import andoridhost.imczy.com.activitymaterial.custom.ChangeColor;
import andoridhost.imczy.com.activitymaterial.custom.ChangePosition;
import andoridhost.imczy.com.activitymaterial.custom.MyReturnRevealTransition;
import andoridhost.imczy.com.activitymaterial.custom.MyRevealTransition;
import andoridhost.imczy.com.activitymaterial.custom.ReturnChangePosition;

/**
 * Created by chenzhiyong on 16/6/6.
 */
public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";


    View bottomSendView;
    View topBarView;
    View commentBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);

        bottomSendView = findViewById(R.id.bottom_send_bar);
        topBarView = findViewById(R.id.txt_title_bar);
        commentBox = findViewById(R.id.comment_box);

        getWindow().setEnterTransition(new CommentEnterTransition(this, topBarView, bottomSendView));
        getWindow().setReturnTransition(new CommentReturnTransition(this, topBarView, bottomSendView));


        commentBox.setClipToOutline(true);

        TransitionSet allSet = new TransitionSet();

        Transition changePos = new ChangePosition();
        changePos.setDuration(300);
        changePos.addTarget(R.id.comment_box);
        allSet.addTransition(changePos);
//
        Transition revealTransition = new MyRevealTransition(commentBox);
        allSet.addTransition(revealTransition);
        revealTransition.addTarget(R.id.comment_box);
        revealTransition.setInterpolator(new FastOutSlowInInterpolator());
        revealTransition.setDuration(300);

        ChangeColor changeColor = new ChangeColor(getResources().getColor(R.color.black_85_alpha), getResources().getColor(R.color.white));
        changeColor.addTarget(R.id.comment_box);
        changeColor.setDuration(350);

        allSet.addTransition(changeColor);
//        allSet.addTransition(new ChangeBounds());

        allSet.setDuration(300);


        getWindow().setSharedElementEnterTransition(allSet);

        getWindow().setSharedElementsUseOverlay(true);
        getWindow().setSharedElementReturnTransition(buildReturnSet());


    }

    private TransitionSet buildReturnSet() {
        TransitionSet firstSet = new TransitionSet();

        Transition changePos = new ReturnChangePosition();
        changePos.addTarget(R.id.comment_box);
        firstSet.addTransition(changePos);


        ChangeColor changeColor = new ChangeColor(getResources().getColor(R.color.white), getResources().getColor(R.color.black_85_alpha));
        changeColor.addTarget(R.id.comment_box);
        firstSet.addTransition(changeColor);


        Transition revealTransition = new MyReturnRevealTransition(commentBox);
        revealTransition.addTarget(R.id.comment_box);
        firstSet.addTransition(revealTransition);

        firstSet.setDuration(300);

        firstSet.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Log.e(TAG, "onTransitionEnd: ");
                commentBox.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });


        return firstSet;
    }
}