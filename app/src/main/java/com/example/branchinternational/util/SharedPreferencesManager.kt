package com.example.branchinternational.util

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit


class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val AUTH_TOKEN_KEY = "authToken"
    }

    fun saveAuthToken(authToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(AUTH_TOKEN_KEY, authToken)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }
}


