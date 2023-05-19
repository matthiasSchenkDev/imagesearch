package com.example.imagesearch

import com.example.imagesearch.presentation.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override fun default() = testDispatcher

    override fun io() = testDispatcher

    override fun main() = testDispatcher

}