package com.czy.im.bitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView1;
    ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView1 = (ImageView) findViewById(R.id.img1);
        mImageView2 = (ImageView) findViewById(R.id.img2);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic);

        mImageView2.setImageBitmap(changeBitmap(bitmap));
    }


    private Bitmap changeBitmap(Bitmap bitmap) {
        int[] pixs;
        pixs = new int[bitmap.getWidth() * bitmap.getHeight()];

        bitmap.getPixels(pixs, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < pixs.length; i++) {
            if (pixs[i] == 0xffffffff) {
                pixs[i] = 0x80ffffff;
            }

            pixs[i] = pixs[i] ^ 0x80000000;

        }

        return Bitmap.createBitmap(pixs, 0, bitmap.getWidth(), bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

    }
}
