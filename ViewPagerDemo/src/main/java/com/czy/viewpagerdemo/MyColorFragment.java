//package com.czy.viewpagerdemo;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.imczy.common_util.log.LogUtil;
//
///**
// * Created by chenzhiyong on 15/12/6.
// */
//public class MyColorFragment extends Fragment {
//    public final static String TAG = "MyFragment";
//    public final static String SaveInstanceStateEXTRA = "SaveInstanceStateEXTRA";
//
//
//    private int colorId;
//
//    public static MyColorFragment getInstances(int colorId) {
//        MyColorFragment myFragment = new MyColorFragment();
//        myFragment.colorId = colorId;
//        return myFragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.d(TAG, "onCreateView  mData = " + colorId);
//        String str = "";
//
//        // 这里必须是null
//        View item = inflater.inflate(R.layout.item_1, null);
//        item.setBackgroundColor(getResources().getColor(colorId));
//        TextView textView = (TextView) item.findViewById(R.id.txt);
//        textView.setText("asdasdas");
//        if (!TextUtils.isEmpty(str)) {
//            textView.setText(str);
//        }
//        return item;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        LogUtil.d(TAG, "onDestroy mData = " + colorId);
//    }
//}
