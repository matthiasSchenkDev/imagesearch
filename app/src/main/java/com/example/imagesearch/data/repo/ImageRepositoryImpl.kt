package com.example.imagesearch.data.repo

import com.example.imagesearch.data.api.ImageApi
import com.example.imagesearch.data.api.ImageApi.Companion.DEFAULT_PAGE
import com.example.imagesearch.data.db.ImageDao
import com.example.imagesearch.data.db.SearchDao
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.data.dto.SearchDto
import com.example.imagesearch.data.mapper.ImageDtoMapper
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
    private val imageDao: ImageDao,
    private val searchDao: SearchDao,
    @Named("apiKey") private val apiKey: String
) : ImageRepository {

    private var currentQuery = ""
    private var currentPaginationIndex = DEFAULT_PAGE
    private val paginationCache = mutableListOf<Image>()

    override fun getImagesPaginated(query: String): Flow<NetworkResult<List<Image>>> =
        flow {
            val notAvailableResult =
                NetworkResult.Error<List<Image>>(Throwable("no images available for query: $query"))

            if (currentQuery == query) {
                currentPaginationIndex++
            } else {
                resetPagination()
                currentQuery = query
            }

            val result = try {
                val imageDtoList = getImagesFromCache(query) ?: getImagesFromWeb(query)
                imageDtoList?.let { dtoList ->
                    val images = dtoList.mapNotNull { imageDtoMapper.transform(it) }
                    if (images.isNotEmpty()) {
                        paginationCache.addAll(images)
                        NetworkResult.Success(paginationCache.toList())
                    } else notAvailableResult
                } ?: notAvailableResult
            } catch (e: Throwable) {
                resetPagination()
                currentQuery = ""
                NetworkResult.Error(e)
            }

            emit(result)
        }

    override fun getImage(id: Int): Flow<NetworkResult<Image>> = flow {
        val notAvailableResult =
            NetworkResult.Error<Image>(Throwable("no images available for id: $id"))

        val result = try {
            val imageDto = getImageFromCache(id) ?: getImageFromWeb(id)
            imageDto?.let { dto ->
                imageDtoMapper.transform(dto)?.let { image ->
                    NetworkResult.Success(image)
                } ?: notAvailableResult
            } ?: notAvailableResult
        } catch (e: Throwable) {
            NetworkResult.Error(e)
        }

        emit(result)
    }

    @Throws(Throwable::class)
    private suspend fun getImagesFromWeb(query: String): List<ImageDto>? {
        val imagesDto =
            imageApi.getImages(key = apiKey, query = query, page = currentPaginationIndex)
        return imagesDto.hits?.let {
            val search = SearchDto(query = query, numPage = currentPaginationIndex, images = it)
            searchDao.insertSearch(search)
            it
        }
    }

    private fun getImagesFromCache(query: String): List<ImageDto>? {
        return searchDao.getSearch(query, page = currentPaginationIndex)?.let {
            currentPaginationIndex = it.numPage
            it.images
        }
    }

    private fun resetPagination() {
        paginationCache.clear()
        currentPaginationIndex = DEFAULT_PAGE
    }

    @Throws(Throwable::class)
    private suspend fun getImageFromWeb(id: Int): ImageDto? {
        val imagesDto = imageApi.getImage(key = apiKey, id = id.toString())
        return imagesDto.hits?.firstOrNull()?.let {
            imageDao.insertImage(it)
            it
        }
    }

    private fun getImageFromCache(id: Int): ImageDto? {
        return imageDao.getImage(id)
    }

}