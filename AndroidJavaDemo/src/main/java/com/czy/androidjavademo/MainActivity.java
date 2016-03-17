package com.czy.androidjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    int[] mData = {
            8, 9, 12, 3, 14, 0, 4, 13, 2, 11, 10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < mData.length; i++) {
            Log.d(TAG, " mData [" + i + "] = " + mData[i]);
        }

        quickSort1(mData, 0, mData.length - 1);

        printArray(mData);

        ArrayList list = new ArrayList();
        Collections.sort(list);

    }

    private void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            Log.w(TAG, "printArray: array [" + i + "] = " + array[i]);
        }
    }

    public void quickSort1(int[] data, int l, int r) {
        if (l < r) {
            int i = quciSort(data, l, r);
            quickSort1(data, l, i - 1);
            quickSort1(data, i + 1, r);
        }

    }


    private int quciSort(int[] data, int l, int r) {

        int i = l;
        int j = r;
        int s = data[i];

        while (i < j) {

            while (i < j && data[j] >= s) {
                j--;
            }
            if (i < j) {
                data[i] = data[j];
                i++;
            }

            while (i < j && data[i] < s) {
                i++;
            }

            if (i < j) {
                data[j] = data[i];
                j--;
            }

        }

        data[i] = s;
        return i;
    }
}
