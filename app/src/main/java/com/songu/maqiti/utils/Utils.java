package com.songu.maqiti.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.doc.Globals;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2/20/2017.
 */
public class Utils {
    public static String getResourceString(Context mContext,int res)
    {
        return mContext.getResources().getString(res);
    }
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    public static String getRealPathFromURI(Activity act, Uri contentURI)
    {
        Cursor cursor = act.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public static String getPriceValue(String value)
    {
        String result = "";
        while (value.length() > 3)
        {
            result = "," + value.substring(value.length() - 3) + result;
            value = value.substring(0,value.length() - 3);
        }
        result = value + result;
        return result;
    }
    public static void savePreference(Context mContext)
    {
        SharedPreferences sp = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.edit().putInt("login",1).apply();
        sp.edit().putString("uid", Globals.mAccount.mNo).apply();
        sp.edit().putString("user", Globals.mAccount.mEmail).apply();
        sp.edit().putString("password", Globals.mAccount.mPassword).apply();
    }
    public static void saveCountryPreference(Context mContext)
    {
        SharedPreferences sp = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.edit().putString("countryNo",Globals.currentCountry.mNo).apply();
        sp.edit().putString("countryName",Globals.currentCountry.mName).apply();
        sp.edit().putString("countryImage",Globals.currentCountry.mImage).apply();
    }
    public static void clearPreference(Context mContext)
    {
        SharedPreferences sp = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.edit().putInt("login",0).apply();
        sp.edit().putString("uid", "").apply();
        sp.edit().putString("user", "").apply();
        sp.edit().putString("password", "").apply();
    }
}
