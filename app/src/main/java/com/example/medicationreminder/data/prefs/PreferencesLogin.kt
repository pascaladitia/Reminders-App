package com.example.medicationreminder.data.prefs

import android.content.Context
import androidx.core.content.edit

object PreferencesLogin {

    private const val PREFS_NAME = "login_prefs"
    private const val IS_SAVE_LOGIN = "is_save_login"
    private const val EMAIL = "email"
    private const val FOTO = "foto"

    fun saveIsLogin(
        context: Context,
        email: String,
        isSaveLogin: Boolean
    ) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(EMAIL, email)
            putBoolean(IS_SAVE_LOGIN, isSaveLogin)
        }
    }

    fun getEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(EMAIL, "")
    }

    fun getIsSaveLogin(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_SAVE_LOGIN, false)
    }

    fun saveFoto(context: Context, foto: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(FOTO, foto)
        }
    }

    fun getFoto(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(FOTO, "")
    }

    fun deleteLoginData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            clear()
            commit()
        }
    }
}
