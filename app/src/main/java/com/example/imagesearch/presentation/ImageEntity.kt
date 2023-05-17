package com.example.imagesearch.presentation

data class ImageEntity(
    val id: Int,
    val thumbnailUrlResized: String,
    val tags: String,
    val userName: String
)
