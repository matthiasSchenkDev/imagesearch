package com.example.imagesearch.data

import com.example.imagesearch.data.dto.ImagesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String,
        @Query("q") query: String
    ): ImagesDto

}