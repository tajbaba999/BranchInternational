package com.example.branchinternational.util

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesManager  constructor(private  val sharedPreferences: SharedPreferences){
    companion object{
        private const val KEY_AUTH_TOKEN = ""
    }
    fun saveAuthToken(token : String?){
        sharedPreferences.edit().apply{
            if (token != null){
                putString(KEY_AUTH_TOKEN, token)
            }else{
                remove(KEY_AUTH_TOKEN)
            }
            apply()
        }
    }


    fun getAuthToken() : String?  = sharedPreferences.getString(KEY_AUTH_TOKEN, null)

    fun deleteAuthToken() = saveAuthToken(null)
}

