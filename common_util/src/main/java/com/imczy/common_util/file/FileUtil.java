package com.imczy.common_util.file;

import android.content.Context;
import android.content.res.AssetManager;


import com.imczy.common_util.log.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jackrexxie
 */
public class FileUtil {

    /**
     * @param startPath
     * @param endPath
     */
    public static void moveFile(String startPath, String endPath) throws Exception {

        InputStream inputStream = new FileInputStream(startPath);
        OutputStream outputStream = new FileOutputStream(endPath);
        byte[] buffer = new byte[1024];
        int i;
        while ((i = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, i);

        }
        inputStream.close();
        outputStream.close();
    }

    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            LogUtil.e("asd" + e.getMessage());
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * Copy data from a source stream to destFile. Return true if succeed, return false if failed.
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.flush();
                try {
                    out.getFD().sync();
                } catch (IOException e) {
                }
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() ? file.delete() : false;
    }

    public static void deleteAllFiles(String dir) {
        deleteAllFiles(new File(dir));
    }

    public static void deleteAllFiles(File dirFile) {
        if (dirFile == null) {
            return;
        }

        File files[] = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                LogUtil.v("delete file", file.getName());
                file.delete();
            }
        }
    }

    /**
     * @param context
     * @param srcFile
     * @param destFile
     * @param isCopyForce true 强制性覆盖拷贝，false 已存在则不删除
     *                    从assets下拷贝文件至sd卡
     */
    public static void copyFileFromAssets(Context context, String srcFile, String destFile, boolean isCopyForce) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        try {
            try {
                in = assetManager.open(srcFile);
                File destF = new File(destFile);
                if (destF.exists() && !isCopyForce) {
                    return;
                }
                copyToFile(in, destF);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
        }
    }
}
