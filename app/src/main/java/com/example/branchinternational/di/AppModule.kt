package com.example.branchinternational.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object AppModule {
   fun provideSharedPrefernces(context: Context) : SharedPreferences{
       return context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
   }
}