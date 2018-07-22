package com.example.infosys.seanproject.Application;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.Util.Constant;
import com.example.infosys.seanproject.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MobileApplication extends Application {

    Gson gson = new Gson();

    public void setSelectedProfile(List<Profile> listProfile){
        SharedPreferences.Editor editor = Utils.getMobilePreference(getApplicationContext()).edit();
        editor.putString(Constant.PREF_SELECTED_LIST_PROFILE, gson.toJson(listProfile));
        editor.apply();
    }

    public List<Profile> getSelectedProfile(){
        SharedPreferences sharedPreferences = Utils.getMobilePreference(getApplicationContext());
        String stringSelectedProfile = sharedPreferences.getString(Constant.PREF_SELECTED_LIST_PROFILE,"");
        if(stringSelectedProfile!=null || !"".equals(stringSelectedProfile)){
            return gson.fromJson(stringSelectedProfile, new TypeToken<List<Profile>>(){}.getType());
        }
        return null;
    }
}
