package czy.com.overscrollanimdemo;

import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

public class MainActivity extends AppCompatActivity {

    ViewGroup mMyScrollView;
    RecyclerView mRecyclerView;

    private RecycleViewAdapter mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyScrollView = (ViewGroup) findViewById(R.id.scrollView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        mRecycleViewAdapter = new RecycleViewAdapter(this);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecyclerView.setOutlineProvider(new ViewOutlineProvider() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                }
            });
        }
    }
}
