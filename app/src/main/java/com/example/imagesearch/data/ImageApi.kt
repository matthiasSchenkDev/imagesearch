package com.example.imagesearch.data

import com.example.imagesearch.data.dto.ImagesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    companion object {
        const val DEFAULT_PAGE = 1
    }

    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("page") page: Int,
    ): ImagesDto

    @GET("api/")
    suspend fun getImage(
        @Query("key") key: String,
        @Query("id") id: String
    ): ImagesDto

}