package com.example.imagesearch.presentation.state

import android.os.Parcelable
import com.example.imagesearch.presentation.model.ImageEntity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ImagesListState(
    val images: @RawValue List<ImageEntity>? = listOf(),
    val isLoading: Boolean = false
) : Parcelable
