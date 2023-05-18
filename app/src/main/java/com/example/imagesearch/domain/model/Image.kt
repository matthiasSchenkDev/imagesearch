package com.example.imagesearch.domain.model

data class Image(
    val id: Int,
    val thumbnailUrl: String,
    val tags: String,
    val userName: String,
    val fullImageUrl: String,
    val numLikes: Int,
    val numComments: Int,
    val numDownloads: Int
)
