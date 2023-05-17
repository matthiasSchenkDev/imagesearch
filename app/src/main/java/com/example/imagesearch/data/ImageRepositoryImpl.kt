package com.example.imagesearch.data

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor() : ImageRepository {
    override suspend fun getImages(query: String): Flow<NetworkResult<List<Image>>> {
        TODO("Not yet implemented")
    }
}