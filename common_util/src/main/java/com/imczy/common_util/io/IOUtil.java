package com.imczy.common_util.io;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chenzhiyong on 15/10/28.
 */
public class IOUtil {
    public static final String FS = File.separator;

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8 * 1024);
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Log.e("convertStreamToString", "convertStreamToString error");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }

    public static Bitmap getBitmapFromUrl(URL url) {
        Bitmap bitmap = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new BufferedInputStream(url.openStream(), 4 * 1024);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 4 * 1024);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, bitmapOptions);
            return bitmap;
        } catch (IOException e) {
        } finally {
            closeStream(in);
            closeStream(out);
        }
        return null;
    }

    public static Drawable getDrawableFromUrl(URL url) {
        try {
            InputStream is = url.openStream();
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[4 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public static boolean saveFile(byte[] fileStream, String relativePath, String basePath) {
        ByteArrayOutputStream byteOutputStream = null;
        FileOutputStream fileOutputStream = null;
        boolean isSaveSucc = true;
        try {
            byteOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < fileStream.length; i++) {
                byteOutputStream.write(fileStream[i]);
            }
            fileOutputStream = new FileOutputStream(basePath + FS + relativePath);
            fileOutputStream.write(byteOutputStream.toByteArray());
            byteOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            isSaveSucc = false;
        } finally {
            fileStream = null;
            System.gc();
        }
        return isSaveSucc;
    }

    public static boolean saveImageFile(Context activity, Bitmap bitmap, Bitmap.CompressFormat compressFormat, String relativePath, String basePath) {
        if (bitmap == null)
            return false;
        OutputStream fileOutputStream = null;
        File file = new File(basePath + FS + relativePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file);

            bitmap.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // MediaStore.Images.Media.insertImage(activity.getContentResolver(),
            // file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean saveImageFile(Context activity, Bitmap bitmap, Bitmap.CompressFormat compressFormat, String relativePath, String basePath, int quality) {
        if (bitmap == null)
            return false;
        OutputStream fileOutputStream = null;
        File file = new File(basePath + FS + relativePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file);

            bitmap.compress(compressFormat, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // MediaStore.Images.Media.insertImage(activity.getContentResolver(),
            // file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean saveImageFile(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String relativePath, String basePath) {
        if (bitmap == null)
            return false;
        OutputStream fileOutputStream = null;
        File file = new File(basePath + FS + relativePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file);

            bitmap.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // MediaStore.Images.Media.insertImage(activity.getContentResolver(),
            // file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {
            return false;
        } catch (Throwable e) {
            return false;
        } finally {
            fileOutputStream = null;
        }
        return true;
    }

    public static boolean saveImageFile(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String relativePath, String basePath, int quality) {
        if (bitmap == null) {
            return false;
        }
        OutputStream fileOutputStream = null;
        try {
            File file = new File(basePath + FS + relativePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            fileOutputStream = new FileOutputStream(file);

            bitmap.compress(compressFormat, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // MediaStore.Images.Media.insertImage(activity.getContentResolver(),
            // file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) {
            return false;
        } catch (Throwable e) {
            return false;
        } finally {
            fileOutputStream = null;
        }
        return true;
    }

    public static boolean getExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return false;
        } else {
            return false;
        }
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    public static boolean isDirExist(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static boolean makeDirs(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    public static String getPathName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static String getExtentionName(String url) {
        if ((url != null) && (url.length() > 0)) {
            int dot = url.lastIndexOf('.');
            if ((dot > -1) && (dot < (url.length() - 1))) {
                return url.substring(dot + 1);
            }
        }
        return null;
    }

    public static String getBaseLocalLocation(Context context) {
        boolean isSDCanRead = IOUtil.getExternalStorageState();
        String baseLocation = "";
        if (isSDCanRead) {
            baseLocation = IOUtil.getSDCardPath();
        } else {
            baseLocation = context.getFilesDir().getAbsolutePath();
        }
        return baseLocation;
    }

    public static int getFileSize(File file) {
        FileInputStream fis = null;
        int size = 0;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    public static boolean addToSysGallery(Activity activity, String fileAbsolutePath, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), fileAbsolutePath, fileName, fileName);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static void deleteFolder(File folder) {
        String childs[] = folder.list();
        if (childs == null || childs.length <= 0) {
            folder.delete();
        }
        for (int i = 0; i < childs.length; i++) {
            String childName = childs[i];
            String childPath = folder.getPath() + File.separator + childName;
            File filePath = new File(childPath);
            if (filePath.exists() && filePath.isFile()) {
                filePath.delete();
            } else if (filePath.exists() && filePath.isDirectory()) {
                deleteFolder(filePath);
            }
        }

        folder.delete();
    }

    public static void copyAssetsFile(String outFilePath, String assetFile, Context context) {
        InputStream myInput;
        OutputStream myOutput;
        try {
            myOutput = new FileOutputStream(outFilePath);
            myInput = context.getAssets().open(assetFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getFilePathByDB(Activity activity, Uri uri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {

        }
        return "";
    }
}
