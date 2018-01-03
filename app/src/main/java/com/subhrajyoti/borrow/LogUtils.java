package com.subhrajyoti.borrow;


import android.util.Log;




public class LogUtils {
    private static final String LOG_PREFIX = "tc_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(final String tag, String message) {
        Log.i(tag, message);
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        Log.i(tag, message, cause);
    }

    public static void LOGW(final String tag, String message) {
        Log.w(tag, message);
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        Log.w(tag, message, cause);
    }

    public static void LOGE(final String tag, String message) {
        Log.e(tag, message);
    }


    public static void LOGE(final String tag, Exception ex) {
        ex.printStackTrace();
        Log.e(tag, ex.getMessage());
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        Log.e(tag, message, cause);
    }

    public static void LOGD(final String tag, String message, boolean shouldShow) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause, boolean shouldShow) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, String message, boolean shouldShow) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) && shouldShow) {
            Log.v(tag, message);
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause, boolean shouldShow) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        if ((BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) && shouldShow) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(final String tag, String message, boolean shouldShow) {
        if (shouldShow) {
            Log.i(tag, message);
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause, boolean shouldShow) {
        if (shouldShow) {
            Log.i(tag, message, cause);
        }
    }

    public static void LOGW(final String tag, String message, boolean shouldShow) {
        if (shouldShow) {
            Log.w(tag, message);
        }
    }

    public static void LOGW(final String tag, String message, Throwable cause, boolean shouldShow) {
        if (shouldShow) {
            Log.w(tag, message, cause);
        }
    }

    public static void LOGE(final String tag, String message, boolean shouldShow) {
        if (shouldShow) {
            Log.e(tag, message);
        }
    }

    public static void LOGE(final String tag, String message, Throwable cause, boolean shouldShow) {
        if (shouldShow) {
            Log.e(tag, message, cause);
        }
    }

    private LogUtils() {
    }

    public static void LOGLarge(final String tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        int maxLogSize = 1000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.v(tag, message.substring(start, end));
        }
    }

}
