package com.example.mynotes.utils

import android.widget.EditText

object ValidationClass {

    fun isNameEmpty(firstName: EditText): Boolean {
        return firstName.text.toString().isEmpty()
    }
}