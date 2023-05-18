package com.example.imagesearch.data.dto

import com.google.gson.annotations.SerializedName

data class ImagesDto(
    @SerializedName("hits") val hits: List<ImageDto>?,
    @SerializedName("total") val total: Int?,
    @SerializedName("totalHits") val totalHits: Int?
)