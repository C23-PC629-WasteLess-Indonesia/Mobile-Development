package com.example.wasteless.preferences

import android.annotation.SuppressLint
import android.content.Context
import com.example.wasteless.model.dummymodel.UserPref

class UserPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun setUser(value: UserPref){
        val editor = preferences.edit()
        editor.putString(USER_ID, value.userId)
        editor.putString(LAT, value.lat)
        editor.putString(LON, value.lon)
        editor.putString(TOKEN, value.token)
        editor.putBoolean(ISLOGIN, value.isLogin)

        editor.apply()
    }
    fun getUser(): UserPref{
        val model = UserPref()
        model.userId = preferences.getString(USER_ID, "")
        model.lat = preferences.getString(LAT,"")
        model.lon = preferences.getString(LON,"")
        model.token = preferences.getString(TOKEN, "")
        model.isLogin = preferences.getBoolean(ISLOGIN, false)

        return model
    }
    fun removeUser(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val USER_ID = "user_id"
        private const val LAT = "lat"
        private const val LON = "lon"
        private const val TOKEN = "token"
        private const val ISLOGIN = "islogin"
    }
}