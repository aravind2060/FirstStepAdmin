package com.example.firststepadmin.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPreferenceClass {

    public static void setDataIntoSharedPreference(Context context)
    { SharedPreferences sharedPreferences=context.getSharedPreferences("CurrentLoggedInUserDetails",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Name", D_CurrentUser.getName());
        editor.putString("Email",D_CurrentUser.getEmail());
        editor.putString("ProfileImage",D_CurrentUser.getProfileImage());
        editor.apply();
    }

    public static void getDataFromSharedPreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("CurrentLoggedInUserDetails",0);
        String EmailData=sharedPreferences.getString("Email","");
        if (!TextUtils.isEmpty(EmailData))
        {
            D_CurrentUser.setEmail(EmailData);
            D_CurrentUser.setName(sharedPreferences.getString("Name",""));
            D_CurrentUser.setProfileImage(sharedPreferences.getString("ProfileImage",""));
        }
    }

}
