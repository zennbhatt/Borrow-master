package com.subhrajyoti.borrow;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pref {

    private  static final String PREF_FILE = "HDFC_ETE_PREF";
    private static SharedPreferences sharedPreferences = null;

    public static void openPref(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE,Context.MODE_PRIVATE);
    }

    public static String getValue(Context context, String key,String defaultValue) {
        Pref.openPref(context);
        String result = Pref.sharedPreferences.getString(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setValue(Context context, String key, String value) {
        Pref.openPref(context);
        Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putString(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    //int values
    public static void setValue(Context context, String key, int value) {
        Pref.openPref(context);
        Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putInt(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static int getValue(Context context, String key, int defaultValue) {
        Pref.openPref(context);
        int result = Pref.sharedPreferences.getInt(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    //boolean values
    public static boolean getValue(Context context, String key, Boolean defaultValue) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setValue(Context context, String key, Boolean value) {
        Pref.openPref(context);
        Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    //float
    public static void setValue(Context context, String key, float value) {
        Pref.openPref(context);
        Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putFloat(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static float getValue(Context context, String key, float defaultValue) {
        Pref.openPref(context);
        float result = Pref.sharedPreferences.getFloat(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    public static boolean setArray(boolean[] array, String arrayName, Context mContext) {
        Pref.openPref(mContext);
        Editor prefsPrivateEditor = Pref.sharedPreferences.edit();

        prefsPrivateEditor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            prefsPrivateEditor.putBoolean(arrayName + "_" + i, array[i]);
        return prefsPrivateEditor.commit();
    }

    public static boolean[] getArray(String arrayName, Context mContext) {
        Pref.openPref(mContext);

        int size = Pref.sharedPreferences.getInt(arrayName + "_size", 0);
        boolean array[] = new boolean[size];
        for(int i=0;i<size;i++)
            array[i] = Pref.sharedPreferences.getBoolean(arrayName + "_" + i, false);
        return array;
    }

}