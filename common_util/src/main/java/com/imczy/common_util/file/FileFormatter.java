package com.imczy.common_util.file;

import android.content.Context;

import com.imczy.common_util.R;

/**
 * Created by dengshengjin on 15/4/23.
 */
public class FileFormatter {
    /**
     * Formats a content size to be in the form of bytes, kilobytes, megabytes, etc
     *
     * @param context Context to use to load the localized units
     * @param number  size value to be formatted
     * @return formatted string with the number
     */
    public static String formatFileSize(Context context, long number) {
        return formatFileSize(context, number, false, true);
    }

    /**
     * Like {@link #formatFileSize}, but trying to generate shorter numbers
     * (showing fewer digits of precision).
     */
    public static String formatShortFileSize(Context context, long number) {
        return formatFileSize(context, number, true, true);
    }

    public static String formatFileSize(Context context, long number, boolean shorter, boolean isNeedDot) {
        if (context == null) {
            return "";
        }

        float result = number;
        String suffix = "B";
        if (result > 900) {
            suffix = "KB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "MB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "GB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "TB";
            result = result / 1024;
        }
        if (result > 900) {
            suffix = "PB";
            result = result / 1024;
        }
        String value;
        if (result < 1) {
            if (isNeedDot) {
                value = String.format("%.2f", result);
            } else {
                value = String.format("%.0f", result);
            }
        } else if (result < 10) {
            if (isNeedDot) {
                if (shorter) {
                    value = String.format("%.1f", result);
                } else {
                    value = String.format("%.2f", result);
                }
            } else {
                value = String.format("%.0f", result);
            }
        } else if (result < 100) {
            if (isNeedDot) {
                if (shorter) {
                    value = String.format("%.1f", result);
                } else {
                    value = String.format("%.2f", result);
                }
            } else {
                value = String.format("%.0f", result);
            }
        } else {
            value = String.format("%.0f", result);
        }
        return context.getResources().
                getString(R.string.fileSizeSuffix,
                        value, suffix);
    }


}
