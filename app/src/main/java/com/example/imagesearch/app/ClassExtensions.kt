package com.example.imagesearch.app

import android.view.View

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