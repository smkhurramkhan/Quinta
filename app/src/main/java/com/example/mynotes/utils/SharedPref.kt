package com.example.mynotes.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context?) {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    init {
        if (context != null) {
            prefs = context.getSharedPreferences("NotificationHistoryPrefs", Context.MODE_PRIVATE)
            editor = prefs.edit()
            editor.apply()
        }
    }

    var isFirstTime: Boolean
        get() = prefs.getBoolean("firstTime", true)
        set(flag) {
            editor.putBoolean("firstTime", flag)
            editor.commit()
        }

    var personName: String?
        get() = prefs.getString("personName", "User")
        set(value) {
            editor.putString("personName", value)
            editor.commit()
        }
}