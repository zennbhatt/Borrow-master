package com.subhrajyoti.borrow;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.graphics.Paint.ANTI_ALIAS_FLAG;


/**
 * This class contains Utility methods for this application
 *
 * @author Zen Bhatt
 */
public class Utility {

    public static int minFileSize = 500;
    public static int maxFileSize = 3000;
    static NoticeDialogListener mListener;


    /**
     * method to show an alert with OK Button
     *
     * @param message  message of application
     * @param mContext Context of that class to open dialog
     */
    public static void showAlert(String message, Context mContext,
                                 final NoticeDialogListener mDialogListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(mContext.getResources().getString(R.string.app_name));

        // mListener = (NoticeDialogListener) mContext;
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Ok",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mDialogListener.onDialogPositiveClick(dialog);
                    }
                });
        builder.show();

    }

    public static void createDialogOnline(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Thank you, Your data submitted Successfully Your reference number is: 2016/MH/234746");
        //  builder.setMessage("Your reference number is: 2016/MH/234746");
        builder.setCancelable(false);
        builder.setTitle("Online Submission Success Alert");
        builder.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.rgb(196, 54, 53));
        pbutton.setBackground(null);
        pbutton.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);


    }

    public static String getDate() {
        //Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

        //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        //String formattedDate = df.format(c.getTime());

        String formattedDate = getDateFromMillies(System.currentTimeMillis(), "dd/MM/yyyy hh:mm:ss a");
        LogUtils.LOGD("", "====>CURRENT DATE : " + formattedDate);
        return formattedDate;


    }

    public static String getDateFromMillies(long timestampInMilliSeconds, String format) {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate = new SimpleDateFormat(format).format(date);
        return formattedDate;

    }


    public static String getVersionName(Context context) {
        String version = "";

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }


    public static void createDialogAbout(Context context) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Crop Survey App Version : " + getVersionName(context));
        //  builder.setMessage("Your reference number is: 2016/MH/234746");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.rgb(196, 54, 53));
        pbutton.setBackground(null);
        pbutton.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);


    }

    public static int calculateFileSize(String filepath) {
        Log.i("filepath>>>>", filepath);
        File file = new File(filepath);
        long fileSizeInBytes = file.length();
        float fileSizeInKB = fileSizeInBytes / 1024;
        int size = (int) fileSizeInKB;
        //Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        //float fileSizeInMB = fileSizeInKB / 1024;
        return size;
    }


    public static void hideKeyboard(View v, Activity a) {
        final InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static void showAlertWarning(String message, Context mContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Error");

        // mListener = (NoticeDialogListener) mContext;
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Ok",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        //  builder.show();
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        pbutton.setTextColor(Color.rgb(196, 54, 53));
        pbutton.setBackground(null);
        nbutton.setTextColor(Color.rgb(196, 54, 53));
        nbutton.setBackground(null);
        pbutton.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);


    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }


    /**
     * Show an Alert Dialog with Two Buttons Ok,Cancel
     *
     * @param message  message of Alert Dialog
     * @param mContext Context to open dialog
     * @param Listener Listener to call
     */
    public static void showAlertOKCancel(String message, Context mContext,
                                         NoticeDialogListener Listener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(mContext.getResources().getString(R.string.app_name));
        mListener = Listener;

        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Ok",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mListener.onDialogPositiveClick(dialog);
                    }
                });
        builder.setNegativeButton(
                "Cancel",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mListener.onDialogNegativeClick(dialog);
                    }
                });
        builder.show();
    }

    /**
     * method to check Internet connectivity
     *
     * @param context -context of activity
     * @return true if Internet is available
     */
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null

                && connectivityManager.getActiveNetworkInfo().isAvailable()

                && connectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {
            Log.v("INTERNETWORKING", "Internet not present");
            return false;
        }

    }

    /***
     * method to check whether text of Edit text is empty
     *
     * @param editText -edtit text to be checked
     * @return -false if empty
     */
    public static boolean isTextEmpty(EditText editText) {
        if (editText.getText().toString().trim().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method is for to check the email is valid or not
     *
     * @param email :- String from edit text
     * @return
     */

    public static boolean isEmailValid(String email) {
        boolean isValid;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        } else {

            Log.i(".....Email", "Not valid");

            isValid = false;

        }
        return isValid;
    }

    public static boolean isPasscodeValid(String passcode) {
        boolean isValid;
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
        CharSequence inputStr = passcode;

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        } else {

            Log.i(".....Passcode", "Not valid");

            isValid = false;

        }
        return isValid;
    }

    public static boolean validateComplete(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateIncomplete(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateCompleteForAutoComplete(AutoCompleteTextView[] fields) {
        for (int i = 0; i < fields.length; i++) {
            AutoCompleteTextView currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }


    public static String getCurrentDateTime() {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        dateFormatter.setLenient(false);
        Date today = new Date();
        return dateFormatter.format(today).replace(".", "");
    }

    public static String getCurrentDateHyphenFormat() {
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        dateFormatter.setLenient(false);
        Date today = new Date();
        return dateFormatter.format(today).replace(".", "");
    }

    public static String getCurrentDateTimeNewLine() {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        dateFormatter.setLenient(false);
        Date today = new Date();
        return dateFormatter.format(today).replace(".", "");
    }

    public static void verifyStoragePermissions(Activity activity, int REQUEST_EXTERNAL_STORAGE, String[] permission_storage) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    permission_storage,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {


        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static long getMilliesFromDate(String strDate, String format) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            Date d = f.parse(strDate);
            return d.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static JSONObject getJsonObjectFromMap(Map params) throws JSONException {

        //all the passed parameters from the post request
        //iterator used to loop through all the parameters
        //passed in the post request
        Iterator iter = params.entrySet().iterator();

        //Stores JSON
        JSONObject holder = new JSONObject();

        //using the earlier example your first entry would get email
        //and the inner while would get the value which would be 'foo@bar.com'
        //{ fan: { email : 'foo@bar.com' } }

        //While there is another entry
        while (iter.hasNext()) {
            //gets an entry in the params
            Map.Entry pairs = (Map.Entry) iter.next();

            //creates a key for Map
            String key = (String) pairs.getKey();

            //Create a new map
            Map m = (Map) pairs.getValue();

            //object for storing Json
            JSONObject data = new JSONObject();

            //gets the value
            Iterator iter2 = m.entrySet().iterator();
            while (iter2.hasNext()) {
                Map.Entry pairs2 = (Map.Entry) iter2.next();
                data.put((String) pairs2.getKey(), (String) pairs2.getValue());
            }

            //puts email and 'foo@bar.com'  together in map
            holder.put(key, data);
        }
        return holder;
    }

    public static void showSnackbar(Context context, String msg) {
        View view = ((Activity) context).findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View sbView = snack.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
        sbView.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        textView.setMaxLines(3);
        snack.show();
    }

/*    public static Bitmap decodeSampledBitmapFromFile(String file,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }*/


/*    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }*/

    public static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogInterface builder);

        void onDialogNegativeClick(DialogInterface dialog);
    }


}
