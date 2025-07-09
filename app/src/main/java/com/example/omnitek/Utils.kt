package com.example.omnitek

import android.widget.EditText

fun EditText.isEmpty(): Boolean {
    return if (this.text.toString().isEmpty()) {
        this.error = "Field cannot be empty"
        true
    } else {
        false
    }
}
