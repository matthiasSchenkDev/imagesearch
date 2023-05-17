package com.example.imagesearch.data

import android.util.Log
import com.example.imagesearch.app.LOG_TAG
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class ImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
    @Named("apiKey") private val apiKey: String
) : ImageRepository {
    override suspend fun getImages(query: String): Flow<NetworkResult<List<Image>>> = flow {
        val images = imageApi.getImages(key = apiKey, query = query)
        Log.d(LOG_TAG, "images fetched for query '$query': ${images.totalHits.toString()}")
        emit(NetworkResult.Success(listOf()))
    }

}