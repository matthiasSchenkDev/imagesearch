package com.example.imagesearch.presentation

import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProvider : DispatcherProvider {

    override fun default() = Dispatchers.Default

    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

}