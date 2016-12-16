package imczy.com.allowbackuptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imczy.common_util.db.SettingUtils;

public class MainActivity extends AppCompatActivity {

    Button testBtn;
    TextView testTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingUtils.init(this);

        testBtn = (Button) findViewById(R.id.test_btn);
        testTxt = (TextView) findViewById(R.id.test_txt);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingUtils.setDanmukuUudi("12312312312");
                testTxt.setText(SettingUtils.getDanmukuUuidString());
            }
        });

        testTxt.setText(SettingUtils.getDanmukuUuidString());
    }
}
