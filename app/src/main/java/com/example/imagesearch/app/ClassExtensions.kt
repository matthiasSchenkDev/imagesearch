package com.example.imagesearch.app

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

val Any.LOG_TAG: String
    get() = this::class.java.simpleName

fun checkNotNullOrEmpty(value: String?) {
    if (value.isNullOrEmpty()) {
        throw IllegalStateException()
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Activity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}