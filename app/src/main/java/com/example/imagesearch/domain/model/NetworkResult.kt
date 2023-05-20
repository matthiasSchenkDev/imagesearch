package com.example.imagesearch.domain.model

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val value: T) : NetworkResult<T>()
    class Error<T : Any>(val value: Throwable) : NetworkResult<T>()
}