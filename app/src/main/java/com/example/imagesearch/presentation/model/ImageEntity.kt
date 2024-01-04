package com.example.imagesearch.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageEntity(
    val id: Int,
    val thumbnailUrl: String,
    val tags: String,
    val userName: String,
    val fullImageUrl: String,
    val numLikes: Int,
    val numComments: Int,
    val numDownloads: Int
) : Parcelable
