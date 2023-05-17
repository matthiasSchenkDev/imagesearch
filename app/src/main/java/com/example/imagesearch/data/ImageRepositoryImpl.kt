package com.example.imagesearch.data

import android.util.Log
import com.example.imagesearch.app.LOG_TAG
import com.example.imagesearch.data.ImageApi.Companion.DEFAULT_PAGE
import com.example.imagesearch.domain.model.Image
import com.example.imagesearch.domain.model.NetworkResult
import com.example.imagesearch.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val imageApi: ImageApi,
    private val imageDtoMapper: ImageDtoMapper,
    @Named("apiKey") private val apiKey: String
) : ImageRepository {

    private var currentQuery = ""
    private var paginationIndex = DEFAULT_PAGE
    private val queryCache = mutableListOf<Image>()

    override suspend fun getImagesPaginated(query: String): Flow<NetworkResult<List<Image>>> =
        flow {
            if (currentQuery == query) {
                paginationIndex++
            } else {
                resetPagination()
                currentQuery = query
            }

            val result = try {
                val imagesDto =
                    imageApi.getImages(key = apiKey, query = query, page = paginationIndex)
                Log.d(LOG_TAG, "images fetched for query '$query': ${imagesDto.hits}")
                val images = imagesDto.hits?.mapNotNull { imageDtoMapper.transform(it) }
                if (images.isNullOrEmpty()) {
                    NetworkResult.Error(Throwable("no images available for this query"))
                } else {
                    queryCache.addAll(images)
                    NetworkResult.Success(queryCache.toList())
                }
            } catch (e: Throwable) {
                resetPagination()
                currentQuery = ""
                NetworkResult.Error(e)
            }
            emit(result)
        }

    private fun resetPagination() {
        queryCache.clear()
        paginationIndex = DEFAULT_PAGE
    }

}