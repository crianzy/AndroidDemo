package com.czy.dexdemo;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.imczy.common_util.file.FileUtil;
import com.imczy.common_util.io.IOUtil;
import com.imczy.common_util.log.LogUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = (ImageView) findViewById(R.id.img);
        getCacheDir();
        mResource = getResources();
        LogUtil.d(TAG, "getCacheDir() = " + getCacheDir().getAbsolutePath());
        LogUtil.d(TAG, "getFilesDir() = " + getFilesDir().getAbsolutePath());
        LogUtil.d(TAG, "getPackageResourcePath() = " + getPackageResourcePath());
        LogUtil.d(TAG, "getPackageCodePath() = " + getPackageCodePath());

        final String themeApkPath = IOUtil.getBaseLocalLocation(this) + File.separator + "ThemeDemo.apk";

//        final String copyPath = getFilesDir().getAbsolutePath() + File.separator + "ThemeDemo.apk";
//
//        boolean res = FileUtil.copyFile(new File(themeApkPath), new File(copyPath));
//        LogUtil.d(TAG, "copy res = " + res);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //是dex的输出路径(因为加载apk/jar的时候会解压出dex文件，这个路径就是保存dex文件的)
                String optimizedDirectory = getCacheDir() + File.separator;
                //DexClassLoader可以加载任何路径的apk/dex/jar 这里要注意了PathClassLoader只能加载/data/app中的apk，也就是已经安装到手机中的apk。
                //这个也是PathClassLoader作为默认的类加载器的原因，因为一般程序都是安装了，
                ClassLoader classLoader = new DexClassLoader(themeApkPath, optimizedDirectory, null, getClassLoader());
                //把 theme apk 中的Res 加载进来
                addOtherResourcesToMain(themeApkPath);

                try {
                    // 反射 执行 方法
                    Class clazz = classLoader.loadClass("com.czy.themedemo.ThemeUtil");
                    Method method = clazz.getMethod("getImg", Resources.class);
                    Drawable drawable = (Drawable) method.invoke(null, mResource);
                    LogUtil.d(TAG, "drawable = " + drawable);
                    mImageView.setBackgroundDrawable(drawable);
                } catch (Exception e) {
                    LogUtil.d(TAG, "e = " + e.getMessage());
                    e.printStackTrace();
                }

                //同 R 直接过去 Img res  也 ok
//                int drawable_demo_id = mResource.getIdentifier("demo", "drawable", "com.czy.themedemo");
//                Drawable draw = mResource.getDrawable(drawable_demo_id);
//                LogUtil.d(TAG, "draw = " + draw);
//                if (draw != null) {
//                    mImageView.setBackgroundDrawable(draw);
//                }

            }
        });
    }


    Resources mResource;
    AssetManager mAssetManager;

    //这个方法把我们主题apk里的resource 加入到我们自己的主apk里的resource里
    //这个dexPath就是 我们theme.apk在 我们主apk 的存放路径
    private void addOtherResourcesToMain(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //反射调用addAssetPath这个方法 就可以
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //把themeapk里的资源 通过addAssetPath 这个方法增加到本apk自己的path里面以后 就可以重新构建出resource对象了
        mResource = new Resources(mAssetManager, getResources().getDisplayMetrics(), getResources().getConfiguration());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
