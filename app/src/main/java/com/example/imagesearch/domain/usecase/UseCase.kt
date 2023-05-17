package com.example.imagesearch.domain.usecase

import com.example.imagesearch.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UseCase<T : Any> {
    suspend fun build(params: UseCaseParams? = null): Flow<NetworkResult<T>>
}