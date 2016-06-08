package andoridhost.imczy.com.activitymaterial;

import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;

import java.util.List;
import java.util.Map;

import andoridhost.imczy.com.activitymaterial.common.CommentEnterTransition;
import andoridhost.imczy.com.activitymaterial.common.CommentReturnTransition;
import andoridhost.imczy.com.activitymaterial.custom.ChangeColor;
import andoridhost.imczy.com.activitymaterial.custom.ChangePosition;
import andoridhost.imczy.com.activitymaterial.custom.MyRevealTransition;

/**
 * Created by chenzhiyong on 16/6/6.
 */
public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";


    View bottomSendView;
    View topBarView;
    View CommentBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);

        bottomSendView = findViewById(R.id.bottom_send_bar);
        topBarView = findViewById(R.id.txt_title_bar);
        CommentBox = findViewById(R.id.comment_box);

        getWindow().setEnterTransition(new CommentEnterTransition(this, topBarView, bottomSendView));
        getWindow().setReturnTransition(new CommentReturnTransition(this, topBarView, bottomSendView));


        TransitionSet allSet = new TransitionSet();

        Transition changePos = new ChangePosition();
        changePos.setDuration(300);
        changePos.addTarget(R.id.comment_box);
        allSet.addTransition(changePos);

        Transition revealTransition = new MyRevealTransition(CommentBox);
        allSet.addTransition(revealTransition);
        revealTransition.setInterpolator(new FastOutSlowInInterpolator());
        revealTransition.setDuration(300);

        ChangeColor changeColor = new ChangeColor(getResources().getColor(R.color.black_85_alpha), getResources().getColor(R.color.white));
        changeColor.addTarget(R.id.comment_box);
        changeColor.setDuration(350);

        allSet.addTransition(changeColor);


        getWindow().setSharedElementEnterTransition(allSet);


        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                findViewById(R.id.comment_box).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                findViewById(R.id.comment_box).setVisibility(View.VISIBLE);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                findViewById(R.id.comment_box).setVisibility(View.INVISIBLE);
            }
        });
    }
}