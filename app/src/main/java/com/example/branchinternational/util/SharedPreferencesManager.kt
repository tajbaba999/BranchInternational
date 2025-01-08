package com.example.branchinternational.util

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

class SharedPreferencesManager  constructor(private  val sharedPreferences: SharedPreferences){

    fun saveAuthToken(token: String) {
        Log.d("SharedPreferences", "Saving auth token: $token")
        sharedPreferences.edit().putString("authToken", token).apply()
    }

    fun getAuthToken(): String? {
        val token = sharedPreferences.getString("authToken", null)
        Log.d("SharedPreferences", "Retrieved auth token: $token")
        return token
    }


    fun deleteAuthToken() {
        sharedPreferences.edit().remove("authToken").apply()
    }
}

