package com.example.imagesearch.app

val Any.LOG_TAG: String
    get() = this::class.java.simpleName

fun checkNotEmpty(value: String) = value.ifEmpty { throw IllegalStateException() }