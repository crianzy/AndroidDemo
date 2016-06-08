package andoridhost.imczy.com.transz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.rv);

        View view1 = new MyFrameView(this);
        View view2 = new MyFrameView(this);


        viewGroup.addView(view1, new ViewGroup.LayoutParams(300, 300));
        viewGroup.addView(view2, new ViewGroup.LayoutParams(300, 300));

        view1.setTranslationZ(20);

        view2.setTranslationY(90);
        view2.setTranslationX(90);
        view2.setTranslationZ(200);

    }
}
