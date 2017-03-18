package com.tt.ly.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.Map;


/**
 * Created by Seven on 17/3/18.
 */
public class ConfigUtils {

    public static String getConfigByName(Context context, String propName){
        if(context == null || TextUtils.isEmpty(propName)){
            return null;
        }

        return context
                .getSharedPreferences("ResourceConfig",Activity.MODE_APPEND)
                .getString(propName,"default");
    }

    public static Map getAllConfigs(Context context){
        if(context == null){
            return null;
        }

        return context
                .getSharedPreferences("ResourceConfig",Activity.MODE_APPEND)
                .getAll();
    }

    public static boolean setConfigWithNameValue(Context context,String propName, String value){
        if(context == null || TextUtils.isEmpty(propName) || TextUtils.isEmpty(value)){
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("ResourceConfig",Activity.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(propName,value);
        editor.commit();
        return true;
    }

}
