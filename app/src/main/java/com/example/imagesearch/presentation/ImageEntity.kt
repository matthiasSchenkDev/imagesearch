package com.example.imagesearch.presentation

data class ImageEntity(
    val id: Int,
    val thumbnailUrl: String,
    val tags: String,
    val userName: String,
    val fullImageUrl: String,
    val numLikes: Int,
    val numComments: Int,
    val numDownloads: Int
)
