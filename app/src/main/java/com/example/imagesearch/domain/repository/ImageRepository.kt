package com.example.imagesearch.domain.repository

import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getImagesPaginated(query: String): Flow<NetworkResult<List<Image>>>

    fun getImage(id: Int): Flow<NetworkResult<Image>>

}