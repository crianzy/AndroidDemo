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
//public class MyFragment extends Fragment {
//    public final static String TAG = "MyFragment";
//    public final static String SaveInstanceStateEXTRA = "SaveInstanceStateEXTRA";
//
//
//    String mData;
//
//    public static MyFragment getInstances(String data) {
//        MyFragment myFragment = new MyFragment();
//        myFragment.mData = data;
//        return myFragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.d(TAG, "onCreateView  mData = " + mData);
//        String str = "";
//        if (savedInstanceState != null) {
//            str = savedInstanceState.getString("SaveInstanceStateEXTRA");
//        }
//
//        // 这里必须是null
//        View item = inflater.inflate(R.layout.item_1, null);
//        TextView textView = (TextView) item.findViewById(R.id.txt);
//        textView.setText(mData);
//        if (!TextUtils.isEmpty(str)) {
//            textView.setText(str);
//        }
//        return item;
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(SaveInstanceStateEXTRA, mData + "_save");
//        LogUtil.d(TAG, "onSaveInstanceState  mData = " + mData);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        LogUtil.d(TAG, "onDestroy mData = " + mData);
//    }
//}
