package com.czy.listpopupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxt = (TextView) findViewById(R.id.txt);

        mTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListPopupWindow(mTxt);
            }
        });
    }


    public void showListPopupWindow(View view) {
        String items[] = {"0", "1", "2"};
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);

        // ListView适配器
        listPopupWindow.setAdapter(
                new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, items));

        // 选择item的监听事件
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), "选择:" + pos, Toast.LENGTH_SHORT).show();
                // listPopupWindow.dismiss();
            }
        });

        listPopupWindow.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);

        // 对话框的宽高
        listPopupWindow.setWidth(300);
//        listPopupWindow.setHeight(600);

        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        listPopupWindow.setAnchorView(view);

        // ListPopupWindow 距锚view的距离
        listPopupWindow.setHorizontalOffset(0);
        listPopupWindow.setVerticalOffset(0);

        listPopupWindow.setModal(true);

        listPopupWindow.show();
    }

}
