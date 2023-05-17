package com.example.imagesearch.domain.repository

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getImages(query: String): Flow<NetworkResult<List<Image>>>

}