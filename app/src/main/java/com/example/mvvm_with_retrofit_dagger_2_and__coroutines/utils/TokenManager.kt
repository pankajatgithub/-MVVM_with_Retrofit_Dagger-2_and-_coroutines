package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils

import android.content.Context
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.Constants.PREFS_TOKEN_FILE
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context :Context)
{
   //creating shared preference object
    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    //save token using editor object
    fun saveToken(token : String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply() //to save values
    }

    fun getToken():String?{
        return prefs.getString(USER_TOKEN,null)
    }

}