package com.internshalaworkshop.Utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val IS_LOGGED_IN = "isLoggedIn"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }


    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun setEmail(email: String) {
        editor.putString(EMAIL, email)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL, null)
    }

    fun setPassword(password: String) {
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(PASSWORD, null)
    }
}
