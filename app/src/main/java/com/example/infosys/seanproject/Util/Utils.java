package com.example.infosys.seanproject.Util;

import android.content.Context;
import android.content.SharedPreferences;

public final class Utils {
    public static SharedPreferences getMobilePreference(Context context){
        return context.getSharedPreferences(Constant.MOBILE_PREFERENCE, 0);
    }
}
