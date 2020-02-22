package com.example.agrocheckmake;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SharedPrefManagerFarmer
{
    private static SharedPrefManagerFarmer mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String SHARED_PREF_NAME_1 = "mysharedpref1";

    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_NAME = "userfullname";
    private static final String KEY_USER_MOBILE = "usermobile";
    private static final String KEY_USER_LAND = "userland";
    private static final String KEY_USER_ADDRESS = "useraddress";
    private static final String KEY_USER_CATEGORY = "usercategory";





    private SharedPrefManagerFarmer(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerFarmer getInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new SharedPrefManagerFarmer(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String email,String fullname,String mobile,String land,String address)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_NAME,fullname);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_LAND,land);
        editor.putString(KEY_USER_ADDRESS,address);

        editor.apply();

        return true;
    }



    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_USER_EMAIL,null) != null)
        {
            return true;
        }
        return false;
    }

    public boolean logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public int getUserId()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 1);
    }

    public String getUserName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserMobile()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MOBILE, null);
    }

    public String getUserLand()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_LAND, null);
    }

    public String getUserAddress()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ADDRESS , null);
    }

    public boolean farmerCategory(String category)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_CATEGORY,category);

        editor.apply();

        return true;
    }

    public String isFarmer()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CATEGORY , null);
    }


//    public String getRegisterCustomerCount()
//    {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_1, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_CUSTOMER_COUNT, null);
//    }
}
