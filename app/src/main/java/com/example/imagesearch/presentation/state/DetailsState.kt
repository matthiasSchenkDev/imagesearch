package com.example.imagesearch.presentation.state

import android.os.Parcelable
import com.example.imagesearch.presentation.model.ImageEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(val imageEntity: ImageEntity? = null, val isLoading: Boolean = false) :
    Parcelable