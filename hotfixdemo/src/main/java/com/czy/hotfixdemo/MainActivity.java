package com.czy.hotfixdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dodola.rocoofix.RocooFix;
import com.imczy.common_util.io.IOUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView mHelloTxt;
    Button mBtn1;
    Button mBtn2;
    Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloTxt = (TextView) findViewById(R.id.hello_txt);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);

        updateHelloTxt();


        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPatch();
                updateHelloTxt();
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHelloTxt();
            }
        });

        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHelloTxt();
            }
        });


    }

    private void updateHelloTxt() {
        Hello hello = new Hello();
        mHelloTxt.setText(hello.getHelloMsg3());
    }

    private void findPatch() {
        File patchDir = new File(IOUtil.getBaseLocalLocation(this) + File.separator + "zzz_test");
        if (!patchDir.exists()) {
            boolean r = patchDir.mkdirs();
            Log.e(TAG, "findPatch: mkdirs result = " + r);
        }

        File patchFile = new File(patchDir.getAbsoluteFile() + File.separator + "patch.jar");
        Log.e(TAG, "findPatch: patchFile.exists() = " + patchFile.exists());
        if (!patchFile.exists()) {
            return;
        }
        RocooFix.applyPatchRuntime(this, patchFile.getAbsolutePath());
    }
}
