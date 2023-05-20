package com.example.imagesearch.data.dto

import com.google.gson.annotations.SerializedName

data class ImagesDto(
    val hits: List<ImageDto>?,
    val total: Int?,
    val totalHits: Int?
)