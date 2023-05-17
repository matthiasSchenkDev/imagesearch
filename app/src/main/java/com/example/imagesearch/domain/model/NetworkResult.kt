package com.example.imagesearch.domain.model

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T>()
    class Error<T : Any>(val e: Throwable) : NetworkResult<T>()
}