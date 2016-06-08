package andoridhost.imczy.com.activitymaterial;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import andoridhost.imczy.com.activitymaterial.custom.ChangeColor;
import andoridhost.imczy.com.activitymaterial.custom.ChangePosition;
import andoridhost.imczy.com.activitymaterial.custom.MyReturnRevealTransition;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    ImageView mCommentImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCommentImg = (ImageView) findViewById(R.id.comment_img);

        mCommentImg.setClipToOutline(true);

        mCommentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                transitionTo(intent);
            }
        });

        getWindow().getSharedElementReturnTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d(TAG, "getSharedElementReturnTransition onTransitionStart() called with: " + "transition = [" + transition + "]");
            }

            @Override
            public void onTransitionEnd(Transition transition) {

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

        getWindow().getSharedElementExitTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d(TAG, "getSharedElementExitTransition onTransitionStart() called with: " + "transition = [" + transition + "]");
            }

            @Override
            public void onTransitionEnd(Transition transition) {

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

        getWindow().getSharedElementReenterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                Log.d(TAG, "getSharedElementReenterTransition onTransitionStart() called with: " + "transition = [" + transition + "]");

            }

            @Override
            public void onTransitionEnd(Transition transition) {

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


//        getWindow().setSharedElementReturnTransition(buildReturnSet());
    }

    private TransitionSet buildReturnSet() {
        TransitionSet allSet = new TransitionSet();

        Transition revealTransition = new MyReturnRevealTransition(mCommentImg);
        allSet.addTransition(revealTransition);

        allSet.setDuration(3000);

        return allSet;
    }

    void transitionTo(Intent i) {

        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false,
                new Pair<>(mCommentImg, "comment")
        );
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }
}
