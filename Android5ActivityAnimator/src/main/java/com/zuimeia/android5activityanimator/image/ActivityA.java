package com.zuimeia.android5activityanimator.image;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.util.Pair;
import android.view.View;

import com.zuimeia.android5activityanimator.R;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class ActivityA extends AppCompatActivity {

    View img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSharedElementEnterTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementExitTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementReenterTransition(new MyAutoTransFrom(this));
        getWindow().setSharedElementReturnTransition(new MyAutoTransFrom(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        getWindow().setSharedElementsUseOverlay(false);

        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityA.this, ActivityB.class);
                Pair<View, String> p1 = Pair.create(img, "img");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(ActivityA.this, p1);
                startActivity(intent, activityOptions.toBundle());
            }
        });
    }
}
