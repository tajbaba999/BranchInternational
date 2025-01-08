package com.example.branchinternational.data.util

import android.content.SharedPreferences

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val AUTH_TOKEN_KEY = "authToken"
    }

    fun saveAuthToken(authToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString(AUTH_TOKEN_KEY, authToken)
        editor.apply() // or editor.commit() for synchronous
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }
}
