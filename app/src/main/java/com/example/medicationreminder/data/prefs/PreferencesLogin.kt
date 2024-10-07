package com.example.medicationreminder.data.prefs

import android.content.Context
import androidx.core.content.edit

object PreferencesLogin {

    private const val PREFS_NAME = "login_prefs"
    private const val IS_SAVE_LOGIN = "is_save_login"

    fun saveIsLogin(
        context: Context,
        isSaveLogin: Boolean
    ) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean(IS_SAVE_LOGIN, isSaveLogin)
        }
    }

    fun getIsSaveLogin(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_SAVE_LOGIN, false)
    }
}
